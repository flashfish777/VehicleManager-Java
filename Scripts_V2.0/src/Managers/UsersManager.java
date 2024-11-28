package Managers;

import java.util.HashMap;
import java.util.List;
import IO.IO;
import Users.*;

/**
 * 用户管理器
 */
public class UsersManager extends IO {
    /** 用户管理器实例 */
    public static UsersManager Instance;

    /** 用户列表 */
    private static List<UserBase> users;
    private static UserBase nowUser;

    /** 创建实例 */
    public static void Init() throws Exception {
        Instance = new UsersManager();
    }

    /** 初始化 */
    public UsersManager() throws Exception {
        users = DataManager.Instance.ReadUsersFromSQL();
        users.add(new Admin());
        users.add(new Merchant());
    }

    /** 登录 */
    public void Login() {
        _Speak("\n登录...(输入0退出)");
        _Speak("\n请输入账号：");

        String userName = _ReadStr();

        if (userName.equals("0")) {
            _Speak("再见！");
            return;
        }

        int index = users.indexOf(new User(userName));
        if (index == -1) {
            _Speak("没有该用户！");
            Login();
        } else {
            nowUser = users.get(index);
            nowUser.Login();
        }
    }

    /** 返回当前用户 */
    public User getNowUser() {
        return (User) nowUser;
    }

    /** 查看所有账户 */
    public void CheckAllUser() {
        for (UserBase user : users) {
            user.print();
        }
    }

    /** 添加账户 */
    public void AddUser() {
        _Speak("用户名：");
        String userName = _ReadStr();
        if (users.contains(new User(userName))) {
            _Speak("该用户已存在！");
            return;
        }

        _Speak("密码：");
        String password = _ReadStr();
        _Speak("昵称：");
        String _name = _ReadStr();

        users.add(new User(userName, password, _name, 0, new HashMap<String, Long>()));
        try {
            SqlManager.Instance.CreateUserTable(userName);
            _Speak("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 删除账户 */
    public void DeleteUser() {
        _Speak("用户名：");
        String userName = _ReadStr();
        int index = users.indexOf(new User(userName));
        if (index == -1) {
            _Speak("该用户不存在！");
            return;
        }

        if (((User) users.get(index)).CheckRent()) {
            _Speak("用户账号内有车辆未归还，不可删除。");
            return;
        }

        users.remove(index);
        try {
            SqlManager.Instance.DeleteTable(userName);
            _Speak("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 保存数据 */
    public List<UserBase> LoadUsers() throws Exception {
        // DataManager.Instance.LoadUsersToSQL(users);
        return users;
    }
}
