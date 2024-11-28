package Users;

import Managers.RentManager;
import Managers.UsersManager;
import Managers.VehicleManager;

/**
 * 商家
 */
public class Merchant extends UserBase {
    public Merchant() {
        super("merchant", "222222", "商家");
    }

    /**初始化 */
    @Override
    public void Init() {
        _Speak("\n1-查看所有车辆");
        _Speak("2-添加车辆");
        _Speak("3-修改车辆信息");
        _Speak("4-删除车辆");
        _Speak("5-查看营业额（租赁记录）");
        _Speak("0-返回登录");
        _Speak("请选择您的操作：");
        int op = _ReadInt();

        switch (op) {
            case 1:
                VehicleManager.Instance.CheckAll();
                Init();
                break;
            case 2:
                VehicleManager.Instance.AddVehicle();
                Init();
                break;
            case 3:
                VehicleManager.Instance.OverrideVehicle();
                Init();
                break;
            case 4:
                VehicleManager.Instance.DeleteVehicle();
                Init();
                break;
            case 5:
                RentManager.Instance.GetIncome();
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
        return null;
    }
}
