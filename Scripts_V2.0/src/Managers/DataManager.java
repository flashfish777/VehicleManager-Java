package Managers;

import java.sql.ResultSet;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import Vehicle.*;
import Users.*;

/**
 * 数据管理器
 */
public class DataManager implements Runnable {
    /** 数据管理器实例 */
    public static DataManager Instance;

    public List<VehicleBase> vehicleData;
    public List<UserBase> usersData;
    public HashMap<Long, String> rentsData;

    @Override
    public void run() {
        try {
            LoadVehicleToSQL(vehicleData);
            LoadUsersToSQL(usersData);
            LoadAllRentsToSQL(rentsData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 初始化 */
    public static void Init() {
        Instance = new DataManager();
    }

    /** 获取各表 */
    public void SetData(List<VehicleBase> vehicleData, List<UserBase> usersData, HashMap<Long, String> rentsData) {
        this.vehicleData = vehicleData;
        this.usersData = usersData;
        this.rentsData = rentsData;
    }

    /** 从数据库获取车辆列表 */
    public List<VehicleBase> ReadVehicleFromSQL() throws Exception {
        List<VehicleBase> vehicle = new ArrayList<VehicleBase>();
        ResultSet set = SqlManager.Instance.GetVehicle();

        String No;
        String type;
        String brand;
        int cost;
        String other;
        boolean isrent;

        while (set.next()) {
            No = set.getString(1);
            type = set.getString(2);
            brand = set.getString(3);
            cost = set.getInt(4);
            other = set.getString(5);
            isrent = set.getBoolean(6);

            switch (type) {
                case "汽车":
                    vehicle.add(new Car(No, brand, cost, other, isrent));
                    break;
                case "客车":
                    vehicle.add(new Bus(No, brand, cost, Integer.parseInt(other), isrent));
                    break;
                case "货车":
                    vehicle.add(new Trunk(No, brand, cost, Float.parseFloat(other), isrent));
                    break;
            }
        }

        return vehicle;
    }

    /** 从数据库获取用户列表 */
    public List<UserBase> ReadUsersFromSQL() throws Exception {
        List<UserBase> users = new ArrayList<UserBase>();
        ResultSet set = SqlManager.Instance.GetUsers();

        String userName;
        String password;
        String _name;
        int money;

        while (set.next()) {
            userName = set.getString(1);
            password = set.getString(2);
            _name = set.getString(3);
            money = set.getInt(4);

            users.add(new User(userName, password, _name, money, ReadRentsFromSQL(userName)));
        }

        return users;
    }

    /** 从数据库获得用户租赁数据 */
    public HashMap<String, Long> ReadRentsFromSQL(String userName) throws Exception {
        HashMap<String, Long> rents = new HashMap<String, Long>();
        ResultSet set = SqlManager.Instance.GetUserRents(userName);
        String No;
        Long time;

        while (set.next()) {
            No = set.getString(1);
            time = set.getLong(2);

            rents.put(No, time);
        }

        return rents;
    }

    /** 从数据库获得全部租赁数据 */
    public HashMap<Long, String> ReadAllRentsFromSQL() throws Exception {
        HashMap<Long, String> allRents = new HashMap<Long, String>();
        ResultSet set = SqlManager.Instance.GetAllRents();
        String No;
        Long time;

        while (set.next()) {
            time = set.getLong(1);
            No = set.getString(2);

            allRents.put(time, No);
        }

        return allRents;
    }

    /** 将车辆列表存到数据库 */
    private void LoadVehicleToSQL(List<VehicleBase> vehicle) throws Exception {
        SqlManager.Instance.ClearTable("vehicle");

        if (vehicle.size() == 0)
            return;

        String query = "INSERT INTO vehicle (No, type, brand, cost, other, isrent) VALUES";

        for (VehicleBase veh : vehicle) {
            query += " " + veh.GetInfo() + ",";
        }

        query = query.substring(0, query.length() - 1);
        SqlManager.Instance.SetData(query);
    }

    /** 将用户列表存到数据库 */
    private void LoadUsersToSQL(List<UserBase> users) throws Exception {
        SqlManager.Instance.ClearTable("users");
        String query = "INSERT INTO users (username, password, user, money) VALUES";
        users.remove(new Admin());
        users.remove(new Merchant());

        if (users.size() == 0)
            return;

        for (UserBase user : users) {
            query += " " + user.GetInfo() + ",";
            LoadRentsToSQL((User) user);
        }

        query = query.substring(0, query.length() - 1);
        SqlManager.Instance.SetData(query);

        users.add(new Admin());
        users.add(new Merchant());
    }

    /** 将用户的租赁数据存到数据库 */
    private void LoadRentsToSQL(User user) throws Exception {
        SqlManager.Instance.ClearTable(user.getUserName());

        if (user.GetRents().size() == 0)
            return;

        String query = String.format("INSERT INTO %s (No, time) VALUES", user.getUserName());

        for (String key : user.GetRents().keySet()) {
            query += String.format(" ('%s', %d),", key, user.GetRents().get(key));
        }

        query = query.substring(0, query.length() - 1);

        SqlManager.Instance.SetData(query);
    }

    /** 将全部租赁数据存到数据库 */
    private void LoadAllRentsToSQL(HashMap<Long, String> allRents) throws Exception {
        SqlManager.Instance.ClearTable("allrents");

        if (allRents.size() == 0)
            return;

        String query = "INSERT INTO allrents (time, No) VALUES";

        for (Long key : allRents.keySet()) {
            query += String.format(" (%d, '%s'),", key, allRents.get(key));
        }

        query = query.substring(0, query.length() - 1);

        SqlManager.Instance.SetData(query);
    }
}
