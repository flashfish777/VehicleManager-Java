package Vehicle;

import IO.IO;

/**
 * 车辆基类
 */
public abstract class VehicleBase extends IO {
    /** 车牌号 */
    protected String key;
    /** 品牌 */
    protected String brand;
    /** 租金/天 */
    protected int cost = 0;
    /** 租借状态 */
    protected boolean isrent = false;

    public VehicleBase(String key, String brand, int cost, boolean isrent) {
        this.key = key;
        this.brand = brand;
        this.cost = cost;
        this.isrent = isrent;
    }

    public VehicleBase(String key) {
        this.key = key;
    }

    /** 返回车牌号 */
    public String getKey() {
        return key;
    }

    /** 返回租金 */
    public int getCost() {
        return cost;
    }

    /** 返回租借状态 */
    public boolean getIsrent() {
        return isrent;
    }

    /** 更新租借状态 */
    public void setIsrent(boolean isrent) {
        this.isrent = isrent;
    }

    /** 输出车辆信息 */
    public abstract void Print();

    /** 获取车辆数据 */
    public abstract String GetInfo();

    /** 设置车辆数据 */
    public void SetInfo() {
        _Speak("请输入品牌：");
        brand = _ReadStr();
        _Speak("请输入租价（天）：");
        cost = _ReadInt();
    }

    /**修改车辆数据 */
    public abstract void ChangeInfo();

    @Override
    public boolean equals(Object obj) {
        if (((VehicleBase)obj).getKey().equals(key))
            return true;
        else
            return false;
    }
}
