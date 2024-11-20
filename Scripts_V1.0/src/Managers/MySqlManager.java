package Managers;

import java.sql.*;
import java.util.Date;

public class MySqlManager {
    public static MySqlManager Instance;

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    public MySqlManager() {
        // TODO:填写端口号（一般为3306）、数据库名称（可以在localhost创建一个vehicleManager）、用户名（一般为root）、密码（我不知道）
        SqlConnector("jdbc:mysql://localhost:***/***", "***", "***");
    }

    // 运行sql语句
    public ResultSet RunSql(String query, boolean result) {
        ResultSet set = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            if (!result)
                preparedStatement.executeUpdate();
            else
                set = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("查询错误");
        }

        return set;
    }

    // 连接
    public void SqlConnector(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("连接成功！");
        } catch (SQLException e) {
            System.out.println("连接失败!");
        } finally {

        }
    }

    // 创建用户账号
    public void CreateUser(String userName, String password, String name) {
        RunSql(String.format("CREATE TABLE %s (No VARCHAR(50) PRIMARY KEY, time LONG)", userName), false);
        RunSql(String.format("INSERT INTO users (username, password, user, money) VALUES ('%s', '%s', '%s', %d)",
                userName, password, name, 0), false);
        System.out.println("添加成功！");
    }

    // 查询user信息（2-密码，3-名字，4-余额）
    public String CheckSome(String userName, int some) {
        String res = null;

        try (ResultSet set = RunSql(String.format("SELECT * FROM users WHERE username = '%s'", userName), true)) {
            if (set.next()) {
                res = set.getString(some);
            } else {
                return "null";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("关闭错误");
            }
        }

        return res;
    }

    // 查询表中所有
    public void CheckAll(String table) {
        try (ResultSet set = RunSql(String.format("SELECT * FROM %s", table), true)) {
            while (set.next()) {
                if (table.equals("vehicle"))
                    System.out.println(set.getString(1) + "\t" + set.getString(2) + "\t" + set.getString(3) + "\t"
                            + set.getString(4) + "\t" + set.getString(5) + "\t" + set.getString(6));
                else if (table.equals("users"))
                    System.out.println(set.getString(1) + "\t" + set.getString(2) + "\t" + set.getString(3) + "\t"
                            + set.getString(4));
                else
                    System.out.println(set.getString(1) + "\t" + new Timestamp(set.getLong(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("关闭错误");
            }
        }
    }

    // 查询一行
    public int Check(String key, boolean checkRent) {
        int type = 0;

        try (ResultSet set = RunSql(String.format("SELECT * FROM vehicle WHERE No = '%s'", key), true)) {
            if (set.next()) {
                System.out.println(set.getString(1) + "\t" + set.getString(2) + "\t" + set.getString(3) + "\t"
                        + set.getString(4) + "\t" + set.getString(5));
                if (!checkRent) {
                    switch (set.getString(2)) {
                        case "汽车":
                            type = 1;
                            break;
                        case "客车":
                            type = 2;
                            break;
                        case "货车":
                            type = 3;
                            break;
                    }
                } else {
                    type = Integer.parseInt(set.getString(6));
                }
            } else {
                type = 10;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("关闭错误");
            }
        }

        return type;
    }

    // 获取数据
    public Long GetInfo(String column, String table, String vehicle) {
        Long res = null;

        try (ResultSet set = RunSql(String.format("SELECT %s FROM %s WHERE No = '%s'", column, table, vehicle), true)) {
            if (set.next())
                res = set.getLong(1);
            else
                res = null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("关闭错误");
            }
        }

        return res;
    }

    // 添加车辆
    public <T> void AddInSql(String No, String type, String brand, int cost, T other) {
        RunSql(String.format(
                "INSERT INTO vehicle (No, type, brand, cost, other, isrent) VALUES ('%s', '%s', '%s', %d, '%s', 0)", No,
                type, brand, cost, other.toString()), false);
        System.out.println("添加成功");
    }

    // 删除
    public void DeleteInSql(String table, String column, String No) {
        RunSql(String.format("DELETE FROM %s WHERE %s = '%s'", table, column, No), false);
    }

    // 修改车辆信息
    public <T> void OverrideInSql(String key, String column, T value) {
        RunSql(String.format("UPDATE vehicle SET %s = '%s' WHERE No = '%s'", column, value.toString(), key), false);
        if (!column.equals("isrent"))
            System.out.println("修改成功");
    }

    // 修改user存款
    public void OverrideInSql(String userName, int money, boolean istake) {
        RunSql(String.format("UPDATE users SET money = '%s' WHERE username = '%s'", money, userName), false);
        if (!istake)
            System.out.println("免密支付成功！");
    }

    // 添加我的车辆
    public void AddUserRent(String accName, String vehicleNo, Date currentDate) {
        RunSql(String.format("INSERT INTO %s (No, time) VALUES ('%s', %d)", accName, vehicleNo, currentDate.getTime()),
                false);
        System.out.println("您已租出车牌号为" + vehicleNo + "的车辆，可在【我的车辆】中查看。");
    }

    // 查询表是否为空
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

    // 删除表
    public void DeleteTable(String table) {
        RunSql(String.format("DROP TABLE %s", table), false);
    }
}
