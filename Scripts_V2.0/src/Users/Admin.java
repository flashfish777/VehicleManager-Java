package Users;

import Managers.UsersManager;

/**
 * 管理员 
 */
public class Admin extends UserBase{
    public Admin() {
        super("admin", "111111", "管理员");
    }

    /**初始化 */
    @Override
    public void Init() {
        _Speak("\n1-查看所有账户");
        _Speak("2-添加账户");
        _Speak("3-删除账户");
        _Speak("0-返回登录");
        _Speak("请选择您的操作：");
        int op = _ReadInt();

        switch (op) {
            case 1:
                UsersManager.Instance.CheckAllUser();
                Init();
                break;
            case 2:
                UsersManager.Instance.AddUser();
                Init();
                break;
            case 3:
                UsersManager.Instance.DeleteUser();
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
