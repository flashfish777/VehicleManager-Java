package Managers;

import java.sql.*;

/**
 * 数据库管理器
 */
public class SqlManager {
    /** 数据库管理器实例 */
    public static SqlManager Instance;

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    /** 创建实例 */
    public static void Init() throws SQLException {
        Instance = new SqlManager();
    }

    /** 初始化 */
    public SqlManager() throws SQLException {
        SqlConnector("jdbc:mysql://localhost:3306/vehiclemanager", "root", "mly20051203");
    }

    /** 连接MySQL数据库 */
    private void SqlConnector(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("连接成功！");
    }

    /** 运行SQL语句 */
    private ResultSet RunSql(String query, boolean result) throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        if (!result)
            preparedStatement.executeUpdate();
        else
            return preparedStatement.executeQuery();

        return null;
    }

    /** 从SQL中获取车辆 */
    public ResultSet GetVehicle() throws SQLException {
        return RunSql("SELECT * FROM vehicle", true);
    }

    /** 从SQL中获取用户 */
    public ResultSet GetUsers() throws SQLException {
        return RunSql("SELECT * FROM users", true);
    }

    /** 从SQL中获取用户租赁车辆 */
    public ResultSet GetUserRents(String userName) throws SQLException {
        return RunSql(String.format("SELECT * FROM %s", userName), true);
    }

    /** 从SQL中获取全部租赁数据 */
    public ResultSet GetAllRents() throws SQLException {
        return RunSql("SELECT * FROM allrents", true);
    }

    /** 从SQL中获取营业额 */
    public long GetIncome() throws SQLException {
        ResultSet set = RunSql("SELECT * FROM iscome", true);
        set.next();
        return set.getLong(1);
    }

    /** 清空表 */
    public void ClearTable(String table) throws SQLException {
        RunSql(String.format("TRUNCATE TABLE %s", table), false);
    }

    /** 删除表 */
    public void DeleteTable(String table) throws SQLException {
        RunSql(String.format("DROP TABLE %s", table), false);
    }

    /** 向SQL中写入数据 */
    public void SetData(String query) throws SQLException {
        RunSql(query, false);
    }

    /** 创建客户表 */
    public void CreateUserTable(String userName) throws SQLException {
        RunSql(String.format("CREATE TABLE %s (No VARCHAR(50) PRIMARY KEY, time LONG)", userName), false);
    }
    
    /** 查询表是否为空 */
    public boolean CheckEnpty(String table) {
        boolean res = false;
        try {
            ResultSet set = RunSql(String.format("SELECT EXISTS(SELECT 1 FROM %s)", table), true);
            if (set.next())
                res = !set.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
