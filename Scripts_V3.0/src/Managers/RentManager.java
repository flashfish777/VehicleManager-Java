package Managers;

import java.sql.Date;
import java.util.List;
import IO.IO;
import RentRecord.RentRecord;

/**
 * 租借管理器
 */
public class RentManager extends IO {
    /** 租借管理器实例 */
    public static RentManager Instance;
    private long income;
    private List<RentRecord> allRents;

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

        for (RentRecord rentRecord : allRents) {
            rentRecord.MPrint();
        }
    }

    /** 获取用户租借的车辆 */
    public void GetUserRents(String userName) {
        for (RentRecord rentRecord : allRents) {
            rentRecord.UPrint(userName);
        }

        _Speak("(还车并支付-1，返回-0)\n请选择：");
        int op = _ReadInt();
        if (op == 0) {
            return;
        } else if (op != 1) {
            _Speak("没有该选项！");
            GetUserRents(userName);
            return;
        }

        _Speak("请输入要还的车的车牌号：");
        String No = _ReadStr();

        long pay = 0;
        for (RentRecord rentRecord : allRents) {
            pay = rentRecord.ReturnVehicle(No);
        }

        if (pay == -1) 
            _Speak("没有该车辆！");
        else
            income += pay;
        
        GetUserRents(userName);
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

        VehicleManager.Instance.GetVehicle(No).setIsrent(true);
        allRents.add(new RentRecord(allRents.size() + 1, No, UsersManager.Instance.getNowUser().getUserName(),
                new Date(new java.util.Date().getTime()), false, 0, UsersManager.Instance.getNowUser().getName()));
        _Speak("您已租出车牌号为" + No + "的车辆，可在【我的车辆】中查看。");
    }
    
    /* 检查用户是否还完车辆 */
    public boolean CheckUser(String userName) {
        return allRents.indexOf(new RentRecord(0, null, userName, null, false, 0, null)) == -1 ? false : true;
    }

    /** 保存数据 */
    public List<RentRecord> LoadAllRents() throws Exception {
        return allRents;
    }
}
