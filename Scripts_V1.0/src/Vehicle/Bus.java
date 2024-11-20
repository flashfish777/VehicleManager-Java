package Vehicle;

import Managers.MySqlManager;

public class Bus extends VehicleBase {
    private int maxPeople;

    public Bus(String key, String brand, int cost) {
        super(key, brand, cost);
    }

    public Bus() {
    }

    @Override
    public boolean AddToSql() {
        if (super.AddToSql())
            return true;
        System.out.println("请输入该款客车的载客量：");
        maxPeople = input.nextInt();
        MySqlManager.Instance.AddInSql(key, "客车", brand, cost, maxPeople);
        return false;
    }

    @Override
    public void WriteToSql(String key) {
        System.out.println("要修改哪一项？（1-品牌，2-租金，3-载客量）");
        super.WriteToSql(key);
    }
}
