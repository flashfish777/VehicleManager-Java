package Systems;

import Managers.SystemManager;
import Managers.UserManager;

public class AdminSystem extends SystemBase {
    private int op;

    public AdminSystem(String userName) {
        super(userName);
        password = "111111";
        _name = "管理员";
    }

    @Override
    public void Init() {
        System.out.println("\n1-查看所有账户");
        System.out.println("2-添加账户");
        System.out.println("3-删除账户");
        System.out.println("0-返回登录");
        System.out.println("请选择您的操作：");
        op = input.nextInt();

        switch (op) {
            case 1:
                UserManager.Instance.CheckAllUser();
                break;
            case 2:
                UserManager.Instance.AddUser();
                break;
            case 3:
                UserManager.Instance.DeleteUser();
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
