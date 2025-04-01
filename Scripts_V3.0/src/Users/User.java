package Users;

import Managers.RentManager;
import Managers.UsersManager;
import Managers.VehicleManager;

/**
 * 客户
 */
public class User extends UserBase {
    /** 钱包 */
    private float money;

    public User(String userName, String password, String _name, float money) {
        super(userName, password, _name);
        this.money = money;
    }

    public User(String userName) {
        super(userName);
    }

    /** 初始化 */
    @Override
    public void Init() {
        _Speak("\n1-查看所有车辆");
        _Speak("2-租车");
        _Speak("3-我的车辆");
        _Speak("4-钱包");
        _Speak("0-返回登录");
        _Speak("请选择您的操作：");
        int op = _ReadInt();

        switch (op) {
            case 1:
                VehicleManager.Instance.CheckAll();
                Init();
                break;
            case 2:
                RentManager.Instance.RentVehicle();
                Init();
                break;
            case 3:
                RentManager.Instance.GetUserRents(userName);
                Init();
                break;
            case 4:
                GetMoney();
                Init();
                break;
            case 0:
                SaveData();
                UsersManager.Instance.Login();
                break;
            default:
                _Speak("没有此操作！");
                Init();
                break;
        }
    }

    /** 获取信息 */
    @Override
    public String GetInfo() {
        return String.format("('%s', '%s', '%s', %.2f)", userName, password, _name, money);
    }

    /** 输出钱包 */
    public void GetMoney() {
        _Speak("您的余额：" + money);
        _Speak("(请输入要充值的金额，输入0返回)");
        int op = _ReadInt();

        if (op == 0) {
            return;
        } else {
            money += op;
            _Speak("免密支付成功！");
        }
    }

    /** 获取钱 */
    public long getMoney() {
        return (long) money;
    }

    /** 更新钱 */
    public void setMoney(long money) {
        this.money -= (float) money;
    }

    /** 获取昵称 */
    public String getName() {
        return _name;
    }
}
