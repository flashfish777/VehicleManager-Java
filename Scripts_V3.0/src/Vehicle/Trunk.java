package Vehicle;

/**
 * 货车
 */
public class Trunk extends VehicleBase {
    /** 载货量 */
    private float maxWeight;

    public Trunk(String key, String brand, int cost, float maxWeight, boolean isrent) {
        super(key, brand, cost, isrent);
        this.maxWeight = maxWeight;
    }

    public Trunk(String key) {
        super(key);
    }

    /** 输出车辆信息 */
    @Override
    public void Print() {
        _Speak(String.format("车牌号：%9s  类型： 货车  品牌：%10s\t租价/天：%10d  载货量：%9.2f  是否租出：%5s", key, brand, cost, maxWeight,
                isrent));
    }

    /** 获取车辆数据 */
    @Override
    public String GetInfo() {
        return String.format("('%s', '货车', '%s', %d, %f, %d)", key, brand, cost, maxWeight, isrent ? 1 : 0);
    }

    /** 设置车辆数据 */
    @Override
    public void SetInfo() {
        super.SetInfo();
        _Speak("请输入载货量：");
        maxWeight = _ReadFloat();
    }

    /** 修改车辆数据 */
    @Override
    public void ChangeInfo() {
        _Speak("要修改哪一项？(1-品牌，2-租金，3-载货量，4-全部)");
        int op = _ReadInt();

        switch (op) {
            case 1:
                brand = _ReadStr();
                break;
            case 2:
                cost = _ReadInt();
                break;
            case 3:
                maxWeight = _ReadFloat();
                break;
            case 4:
                SetInfo();
                break;
            default:
                _Speak("没有该选项，请重新输入：");
                ChangeInfo();
                return;
        }
    }
}
