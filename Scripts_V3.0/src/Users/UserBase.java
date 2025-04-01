package Users;

import IO.IO;
import Managers.DataManager;
import Managers.RentManager;
import Managers.UsersManager;
import Managers.VehicleManager;

/**
 * 用户基类
 */
public abstract class UserBase extends IO {
    /** 用户名 */
    protected String userName;
    /** 密码 */
    protected String password;
    /** 昵称 */
    protected String _name;

    public UserBase(String userName, String password, String _name) {
        this.userName = userName;
        this.password = password;
        this._name = _name;
    }

    public UserBase(String userName) {
        this.userName = userName;
    }

    /** 返回用户名 */
    public String getUserName() {
        return userName;
    }

    /** 获取信息 */
    public abstract String GetInfo();

    /** 登录 */
    public void Login() {
        _Speak("请输入密码：");
        if (_ReadStr().equals(password)) {
            _Speak(_name + "，欢迎您！");
            Init();
        } else {
            _Speak("密码错误！");
            UsersManager.Instance.Login();
        }
    }

    /** 初始化 */
    public abstract void Init();

    @Override
    public boolean equals(Object obj) {
        if (((UserBase) obj).getUserName().equals(userName))
            return true;
        else
            return false;
    }

    /** 输出信息 */
    public void print() {
        _Speak(String.format("用户名：%15s  密码：%15s  昵称：%10s", userName, password, _name));
    }

    /** 保存 */
    public void SaveData() {
        try {
            DataManager.Instance.SetData(VehicleManager.Instance.LoadVehicle(), UsersManager.Instance.LoadUsers(),
                    RentManager.Instance.LoadAllRents());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread setData = new Thread(DataManager.Instance);
        setData.start();
    }
}
