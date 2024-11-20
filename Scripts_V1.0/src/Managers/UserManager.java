package Managers;

import java.util.Scanner;

public class UserManager {
    public static UserManager Instance;

    Scanner input = new Scanner(System.in);

    // 查看所有账户
    public void CheckAllUser() {
        System.out.println("用户名" + "\t" + "密码" + "\t" + "昵称" + "\t" + "钱包");
        MySqlManager.Instance.CheckAll("users");
    }

    // 添加账户
    public void AddUser() {
        System.out.print("用户名：");
        String userName = input.next();
        if (!MySqlManager.Instance.CheckSome(userName, 2).equals("null")) {
            System.out.println("该用户已存在！");
            return;
        }
        System.out.print("密码：");
        String password = input.next();
        System.out.print("昵称：");
        String _name = input.next();

        MySqlManager.Instance.CreateUser(userName, password, _name);
    }

    // 删除账户
    public void DeleteUser() {
        System.out.println("请输入要删除的用户名：");
        String userName = input.next();
        if (MySqlManager.Instance.CheckSome(userName, 2).equals("null")) {
            System.out.println("用户不存在！");
            return;
        }
        if (MySqlManager.Instance.CheckEnpty(userName)) {
            MySqlManager.Instance.DeleteInSql("users", "username", userName);
            MySqlManager.Instance.DeleteTable(userName);
            System.out.println("删除成功！");
        } else {
            System.out.println("用户账号内有车辆未归还，不可删除。");
        }
    }
}
