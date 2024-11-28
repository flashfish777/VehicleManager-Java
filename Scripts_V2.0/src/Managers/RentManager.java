package Managers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import IO.IO;

/**
 * 租借管理器
 */
public class RentManager extends IO {
    /** 租界管理器实例 */
    public static RentManager Instance;
    private long income;
    private HashMap<Long, String> allRents;

    /** 创建实例 */
    public static void Init() throws Exception {
        Instance = new RentManager();
    }

    /** 初始化 */
    public RentManager() throws Exception {
        allRents = DataManager.Instance.ReadAllRentsFromSQL();
        income = SqlManager.Instance.GetIncome();
    }

    /** 获取营业额 */
    public void GetIncome() {
        _Speak("营业额：" + income);
        _Speak("租借记录：");

        for (Long key : allRents.keySet()) {
            String[] str = allRents.get(key).split(",");
            _Speak(String.format("车牌号：%9s  用户：%10s\t%s  时间：%s", str[0], str[1], str[2], new Timestamp(key)));
        }
    }

    /** 获取用户租借的车辆 */
    public void GetUserRents() {
        HashMap<String, Long> rents = UsersManager.Instance.getNowUser().GetRents();
        for (String key : rents.keySet()) {
            _Speak(String.format("车牌号：%9s  开始时间：%s", key, new Timestamp(rents.get(key))));
        }

        _Speak("(还车并支付-1，返回-0)\n请选择：");
        int op = _ReadInt();
        if (op == 0) {
            return;
        } else if (op != 1) {
            _Speak("没有该选项！");
            GetUserRents();
            return;
        }

        _Speak("请输入要还的车的车牌号：");
        String No = _ReadStr();

        Long time = rents.get(No);
        if (time == null) {
            _Speak("没有该车辆！");
            GetUserRents();
            return;
        }

        Date currentDate = new Date();
        int days = (int) ((currentDate.getTime() - time) / 1000 / 3600 / 24) + 1;
        int pay = days * VehicleManager.Instance.GetVehicle(No).getCost();

        if (UsersManager.Instance.getNowUser().getMoney() < pay) {
            _Speak("还车失败！您共借车" + days + "天（不足1天按1天计算），需要支付" + pay + "元，您的余额不足！");
            return;
        }
        
        rents.remove(No);
        UsersManager.Instance.getNowUser().setMoney(pay);
        income += pay;
        allRents.put(currentDate.getTime(), No + "," + UsersManager.Instance.getNowUser().getName() + "," + "还车");
        VehicleManager.Instance.GetVehicle(No).setIsrent(false);
        _Speak("归还成功！您共借车" + days + "天（不足1天按1天计算），已自动支付" + pay + "元");

        GetUserRents();
    }

    /** 借车 */
    public void RentVehicle() {
        _Speak("请输入车牌号：");
        String No = _ReadStr();

        if (VehicleManager.Instance.GetVehicle(No) == null) {
            _Speak("该车辆不存在！");
            return;
        } else if (VehicleManager.Instance.GetVehicle(No).getIsrent()) {
            _Speak("该车辆已被借出！");
            return;
        }

        HashMap<String, Long> rents = UsersManager.Instance.getNowUser().GetRents();
        Date currentDate = new Date();
        rents.put(No, currentDate.getTime());
        UsersManager.Instance.getNowUser().SetRents(rents);
        VehicleManager.Instance.GetVehicle(No).setIsrent(true);
        allRents.put(currentDate.getTime(), No + "," + UsersManager.Instance.getNowUser().getName() + "," + "借出");
        _Speak("您已租出车牌号为" + No + "的车辆，可在【我的车辆】中查看。");
    }

    /** 保存数据 */
    public HashMap<Long, String> LoadAllRents() throws Exception {
        // DataManager.Instance.LoadAllRentsToSQL(allRents);
        SqlManager.Instance.ClearTable("iscome");
        SqlManager.Instance.SetData(String.format("INSERT INTO iscome (iscome) VALUES (%d)", income));

        return allRents;
    }
}
