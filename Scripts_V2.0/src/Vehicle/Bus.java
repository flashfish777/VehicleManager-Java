package Vehicle;

/**
 * 客车
 */
public class Bus extends VehicleBase {
    /** 载客量 */
    private int maxPeople;

    public Bus(String key, String brand, int cost, int maxPeople, boolean isrent) {
        super(key, brand, cost, isrent);
        this.maxPeople = maxPeople;
    }

    public Bus(String key) {
        super(key);
    }

    /** 输出车辆信息 */
    @Override
    public void Print() {
        _Speak(String.format("车牌号：%9s  类型： 客车  品牌：%10s\t租价/天：%10d  载客量：%9d  是否租出：%5s", key, brand, cost, maxPeople,
                isrent));
    }

    /** 获取车辆数据 */
    @Override
    public String GetInfo() {
        return String.format("('%s', '客车', '%s', %d, %d, %d)", key, brand, cost, maxPeople, isrent ? 1 : 0);
    }

    /** 设置车辆数据 */
    @Override
    public void SetInfo() {
        super.SetInfo();
        _Speak("请输入载客量：");
        maxPeople = _ReadInt();
    }

    /** 修改车辆数据 */
    @Override
    public void ChangeInfo() {
        _Speak("要修改哪一项？(1-品牌，2-租金，3-载客量，4-全部)");
        int op = _ReadInt();

        switch (op) {
            case 1:
                brand = _ReadStr();
                break;
            case 2:
                cost = _ReadInt();
                break;
            case 3:
                maxPeople = _ReadInt();
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
