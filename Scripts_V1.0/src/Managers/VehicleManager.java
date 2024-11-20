package Managers;

import java.util.Scanner;
import Vehicle.*;

public class VehicleManager {
    public static VehicleManager Instance;

    private Scanner input = new Scanner(System.in);
    private VehicleBase vehicle;

    // 查看所有车辆
    public void CheckAll() {
        System.out.println("车牌号" + "\t" + "类型" + "\t" + "品牌" + "\t" + "租金/天" + "\t" + "型号/载客量/载货量" + "\t" + "状态");
        MySqlManager.Instance.CheckAll("vehicle");
    }

    // 添加车辆
    public void AddVehicle() {
        System.out.println("\n您要添加什么类型的车：");
        System.out.println("车辆类型（1-汽车，2-客车，3-货车）：");
        int type = input.nextInt();

        switch (type) {
            case 1:
                vehicle = new Car();
                break;
            case 2:
                vehicle = new Bus();
                break;
            case 3:
                vehicle = new Trunk();
                break;
            default:
                System.out.println("没有该车辆类型！");
                return;
        }
        if (vehicle.AddToSql())
            System.out.println("该车辆已存在！");
    }

    // 修改车辆信息
    public void OverrideVehicle() {
        System.out.println("\n请输入要修改车辆的车牌号：");
        String key = input.next();

        switch (MySqlManager.Instance.Check(key, false)) {
            case 10:
                System.out.println("该车辆不存在！");
                return;
            case 1:
                vehicle = new Car();
                break;
            case 2:
                vehicle = new Bus();
                break;
            case 3:
                vehicle = new Trunk();
                break;
        }
        vehicle.WriteToSql(key);
    }

    // 删除车辆
    public void DeleteVehicle() {
        System.out.println("\n请输入要删除车辆的车牌号：");
        String key = input.next();
        MySqlManager.Instance.DeleteInSql("vehicle", "No", key);
        System.out.println("删除成功");
    }
}
