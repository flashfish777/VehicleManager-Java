package Systems;

import Managers.SystemManager;

public class LoginSystem extends SystemBase {
    private String accNum;

    public LoginSystem() {
        System.out.println("\n登录...(输入两个0退出)");
    }

    @Override
    public void Init() {
        System.out.println("\n请输入账号：");
        accNum = input.nextLine();

        switch (accNum) {
            case "admin":
                Login(new AdminSystem(accNum));
                break;
            case "merchant":
                Login(new MerchantSystem(accNum));
                break;
            case "0":
                System.out.println("再见！");
                return;
            default:
                UserSystem user = new UserSystem(accNum);
                if (user._name.equals("null"))
                System.out.println("没有该用户！");
                else
                    Login(user);
                break;
        }
        SystemManager.Instance.Update();
    }

    public void Login(SystemBase system) {
        system.Login();
    }
}
