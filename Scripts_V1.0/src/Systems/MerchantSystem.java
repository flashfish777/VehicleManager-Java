package Systems;

import Managers.RentManager;
import Managers.SystemManager;
import Managers.VehicleManager;

public class MerchantSystem extends SystemBase {
    private int op;

    public MerchantSystem(String userName) {
        super(userName);
        password = "123456";
        _name = "商家";
    }

    @Override
    public void Init() {
        System.out.println("\n1-查看所有车辆");
        System.out.println("2-添加车辆");
        System.out.println("3-修改车辆信息");
        System.out.println("4-删除车辆");
        System.out.println("5-查看营业额（租赁记录）");
        System.out.println("0-返回登录");
        System.out.println("请选择您的操作：");
        op = input.nextInt();

        switch (op) {
            case 1:
                VehicleManager.Instance.CheckAll();
                break;
            case 2:
                VehicleManager.Instance.AddVehicle();
                break;
            case 3:
                VehicleManager.Instance.OverrideVehicle();
                break;
            case 4:
                VehicleManager.Instance.DeleteVehicle();
                break;
            case 5:
                RentManager.Instance.GetRevenue();
                break;
            case 0:
                SystemManager.Instance.ChangeSystem(new LoginSystem());
                break;
            default:
                System.out.println("没有此操作！");
                break;
        }
        SystemManager.Instance.Update();
    }
}
