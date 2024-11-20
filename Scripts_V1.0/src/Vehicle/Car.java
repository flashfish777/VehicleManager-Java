package Vehicle;

import Managers.MySqlManager;

public class Car extends VehicleBase {
    private String maxSpeed;

    public Car(String key, String brand, int cost) {
        super(key, brand, cost);
    }

    public Car() {
    }

    @Override
    public boolean AddToSql() {
        if (super.AddToSql())
            return true;
        System.out.println("请输入该款汽车的型号：");
        maxSpeed = input.next();
        MySqlManager.Instance.AddInSql(key, "汽车", brand, cost, maxSpeed);
        return false;
    }

    @Override
    public void WriteToSql(String key) {
        System.out.println("要修改哪一项？（1-品牌，2-租金，3-类型）");
        super.WriteToSql(key);
    }
}
