package Managers;

import java.util.Scanner;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RentManager {
    Scanner input = new Scanner(System.in);

    public static RentManager Instance;

    private long merchantMoney;
    private HashMap<Date, String> rentings;

    public RentManager() {
        rentings = new HashMap<Date, String>();
    }

    public void GetRevenue() {
        System.out.println("本次总营业额：" + merchantMoney);
        System.out.println("本次租赁记录：");

        for (Map.Entry<Date, String> entry : rentings.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

    public void RentVehicle() {
        System.out.println("请输入要租车辆的车牌号：");
        String vehicleNo = input.next();

        int state = MySqlManager.Instance.Check(vehicleNo, true);
        if (state == 0) {
            MySqlManager.Instance.OverrideInSql(vehicleNo, "isrent", 1);

            Date currentDate = new Date();
            MySqlManager.Instance.AddUserRent(SystemManager.Instance.NowUser(), vehicleNo, currentDate);

            rentings.put(currentDate, vehicleNo + "     租出");
        } else if (state == 1) {
            System.out.println("该车辆已被租出！");
        } else {
            System.out.println("没有该车辆！");
        }
    }

    public void ReturnUserVehicle() {
        System.out.println("车牌号" + "\t" + "租借时间");
        MySqlManager.Instance.CheckAll(SystemManager.Instance.NowUser());
        System.out.println("(还车并支付-1，返回-0)\n请选择：");

        switch (input.nextInt()) {
            case 1:
                System.out.println("请输入您要还的车牌号：");
                String returnVehicle = input.next();

                Long rentDate = MySqlManager.Instance.GetInfo("time", SystemManager.Instance.NowUser(), returnVehicle);
                if (rentDate != null) {
                    Date currentDate = new Date();

                    int days = (int) ((currentDate.getTime() - rentDate) / 1000 / 3600 / 24) + 1;
                    Long pay = MySqlManager.Instance.GetInfo("cost", "vehicle", returnVehicle) * days;

                    Long userMoney = Long
                            .parseLong(MySqlManager.Instance.CheckSome(SystemManager.Instance.NowUser(), 4));
                    if (userMoney - pay >= 0) {
                        MySqlManager.Instance.OverrideInSql(returnVehicle, "isrent", 0);
                        MySqlManager.Instance.DeleteInSql(SystemManager.Instance.NowUser(), "No",returnVehicle);
                        MySqlManager.Instance.OverrideInSql(SystemManager.Instance.NowUser(), (int) (userMoney - pay),
                                true);
                        merchantMoney += pay;

                        System.out.println("归还成功！您共借车" + days + "天（不足1天按1天计算），已自动支付" + pay + "元");
                    } else {
                        System.out.println("还车失败！您共借车" + days + "天（不足1天按1天计算），需要支付" + pay + "元，您的余额不足！");
                    }
                } else {
                    System.out.println("没有该车辆");
                }
                ReturnUserVehicle();
                break;
            case 0:
                return;
            default:
                System.out.println("没有此操作！");
                ReturnUserVehicle();
                break;
        }
    }
}
