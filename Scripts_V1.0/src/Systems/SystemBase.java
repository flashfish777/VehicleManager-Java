package Systems;

import java.util.Scanner;
import Managers.SystemManager;

public abstract class SystemBase {
    protected Scanner input = new Scanner(System.in);

    protected String userName;
    protected String password;
    protected String _name;

    public SystemBase(String userName) {
        this.userName = userName;
    }

    public SystemBase() {
    }

    public abstract void Init();

    public void Login() {
        System.out.println("请输入密码：");
        if (input.next().equals(password)) {
            SystemManager.Instance.ChangeSystem(this);
            System.out.println(_name + "，欢迎您！");
        } else {
            System.out.println("密码错误！");
        }
    }

    public String GetUserName() {
        return userName;
    }
}
