import Managers.*;
import Systems.*;

public class App {
    public static void main(String[] args) {
        MySqlManager.Instance = new MySqlManager();
        SystemManager.Instance = new SystemManager();
        VehicleManager.Instance = new VehicleManager();
        RentManager.Instance = new RentManager();
        UserManager.Instance = new UserManager();

        SystemManager.Instance.ChangeSystem(new LoginSystem());
        SystemManager.Instance.Update();
    }
}