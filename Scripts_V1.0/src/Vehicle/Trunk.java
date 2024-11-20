package Vehicle;

import Managers.MySqlManager;

public class Trunk extends VehicleBase {
    private float maxWeight;

    public Trunk(String key, String brand, int cost)
    {
        super(key, brand, cost);
    }

    public Trunk() { }

    @Override
    public boolean AddToSql() {
        if (super.AddToSql())
            return true;
        System.out.println("请输入该款货车的载货量：");
        maxWeight = input.nextFloat();
        MySqlManager.Instance.AddInSql(key, "货车", brand, cost, maxWeight);
        return false;
    }

    @Override
    public void WriteToSql(String key) {
        System.out.println("要修改哪一项？（1-品牌，2-租金，3-载货量）");
        super.WriteToSql(key);
    }
}
