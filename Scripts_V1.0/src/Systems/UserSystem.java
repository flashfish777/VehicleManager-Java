package Systems;

import Managers.*;

public class UserSystem extends SystemBase {
    private int op;

    public UserSystem(String userName) {
        super(userName);
        password = MySqlManager.Instance.CheckSome(userName, 2);
        _name = MySqlManager.Instance.CheckSome(userName, 3);

    }

    @Override
    public void Init() {
        System.out.println("\n1-查看所有车辆");
        System.out.println("2-租车");
        System.out.println("3-我的车辆");
        System.out.println("4-钱包");
        System.out.println("0-返回登录");
        System.out.println("请选择您的操作：");
        op = input.nextInt();

        switch (op) {
            case 1:
                VehicleManager.Instance.CheckAll();
                break;
            case 2:
                RentManager.Instance.RentVehicle();
                break;
            case 3:
                RentManager.Instance.ReturnUserVehicle();
                break;
            case 4:
                GetMoney();
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

    public void GetMoney() {
        int nowMoney = Integer.parseInt(MySqlManager.Instance.CheckSome(userName, 4));

        System.out.println("您的余额：" + nowMoney);
        System.out.println("(请输入要充值的金额，输入0返回)");
        op = input.nextInt();

        if (op == 0) {
            return;
        } else {
            MySqlManager.Instance.OverrideInSql(userName, op + nowMoney, false);
        }
    }
}
