package Vehicle;

import java.util.Scanner;
import Managers.MySqlManager;

public abstract class VehicleBase {
    protected Scanner input = new Scanner(System.in);

    protected String key;
    protected String brand;
    protected int cost;

    public VehicleBase(String key, String brand, int cost) {
        this.key = key;
        this.brand = brand;
        this.cost = cost;
    }

    public VehicleBase() {
    }

    public boolean AddToSql() {
        System.out.println("请输入车牌号：");
        key = input.next();
        if (MySqlManager.Instance.Check(key, true) != 10) {
            return true;
        }
        System.out.println("请输入品牌：");
        brand = input.next();
        System.out.println("请输入租价（天）：");
        cost = input.nextInt();

        return false;
    }

    public void WriteToSql(String key) {
        int op = input.nextInt();

        switch (op) {
            case 1:
                System.out.println("请输入修改后的值：");
                MySqlManager.Instance.OverrideInSql(key, "brand", input.next());
                break;
            case 2:
                System.out.println("请输入修改后的值：");
                MySqlManager.Instance.OverrideInSql(key, "cost", input.nextInt());
                break;
            case 3:
                System.out.println("请输入修改后的值：");
                MySqlManager.Instance.OverrideInSql(key, "other", input.next());
                break;
            default:
                System.out.println("没有该选项，请重新输入：");
                WriteToSql(key);
                break;
        }
    }
}
