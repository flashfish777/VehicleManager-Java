import Managers.*;

/**
 * 程序入口
 */
public class App {
    public static void main(String[] args) throws Exception {
        // 初始化数据库
        SqlManager.Init();
        // 初始化数据管理器
        DataManager.Init();
        // 初始化车辆管理器
        VehicleManager.Init();
        // 初始化租借管理器
        RentManager.Init();
        // 初始化用户管理器
        UsersManager.Init();

        // 登录
        UsersManager.Instance.Login();
    }
}