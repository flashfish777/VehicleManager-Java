package Managers;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import RentRecord.RentRecord;
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
    public List<RentRecord> rentsData;

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
    public void SetData(List<VehicleBase> vehicleData, List<UserBase> usersData, List<RentRecord> rentsData) {
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
        float money;

        while (set.next()) {
            userName = set.getString(1);
            password = set.getString(2);
            _name = set.getString(3);
            money = set.getFloat(4);

            users.add(new User(userName, password, _name, money));
        }

        return users;
    }

    /** 从数据库获得全部租赁数据 */
    public List<RentRecord> ReadAllRentsFromSQL() throws Exception {
        List<RentRecord> allRents = new ArrayList<RentRecord>();
        ResultSet set = SqlManager.Instance.GetAllRents();

        int rentID;
        String vehicleNo;
        String userName;
        Date time;
        boolean isReturn;
        Long getMoney;
        String Name;

        while (set.next()) {
            rentID = set.getInt(1);
            vehicleNo = set.getString(2);
            userName = set.getString(3);
            time = set.getDate(4);
            isReturn = set.getBoolean(5);
            getMoney = set.getLong(6);
            Name = set.getString(7);

            allRents.add(new RentRecord(rentID, vehicleNo, userName, time, isReturn, getMoney, Name));
        }

        return allRents;
    }

    /** 将车辆列表存到数据库 */
    private void LoadVehicleToSQL(List<VehicleBase> vehicle) throws Exception {
        SqlManager.Instance.ClearTable("vehicles");

        if (vehicle.size() == 0)
            return;

        String query = "INSERT INTO vehicles (no, type, brand, cost, other, isrent) VALUES";

        for (VehicleBase veh : vehicle) {
            query += " " + veh.GetInfo() + ",";
        }

        query = query.substring(0, query.length() - 1);
        SqlManager.Instance.SetData(query);
    }

    /** 将用户列表存到数据库 */
    private void LoadUsersToSQL(List<UserBase> users) throws Exception {
        SqlManager.Instance.ClearTable("users");

        String query = "INSERT INTO users (username, password, name, money) VALUES";
        users.remove(new Admin());
        users.remove(new Merchant());

        if (users.size() == 0)
            return;

        for (UserBase user : users) {
            query += " " + user.GetInfo() + ",";
        }

        query = query.substring(0, query.length() - 1);
        SqlManager.Instance.SetData(query);

        users.add(new Admin());
        users.add(new Merchant());
    }

    /** 将全部租赁数据存到数据库 */
    private void LoadAllRentsToSQL(List<RentRecord> allRents) throws Exception {
        SqlManager.Instance.ClearTable("userrents");

        if (allRents.size() == 0)
            return;

        String query = "INSERT INTO userrents (no, username, time, isreturn, getmoney) VALUES";

        for (RentRecord rentRecord : allRents) {
            query += " " + rentRecord.GetInfo() + ",";
        }

        query = query.substring(0, query.length() - 1);
        SqlManager.Instance.SetData(query);
    }
}
