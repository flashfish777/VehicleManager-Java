package Vehicle;

/**
 * 汽车
 */
public class Car extends VehicleBase {
    /** 型号 */
    private String maxSpeed;

    public Car(String key, String brand, int cost, String maxSpeed, boolean isrent) {
        super(key, brand, cost, isrent);
        this.maxSpeed = maxSpeed;
    }

    public Car(String key) {
        super(key);
    }

    /** 输出车辆信息 */
    @Override
    public void Print() {
        _Speak(String.format("车牌号：%9s  类型： 汽车  品牌：%10s\t租价/天：%10d  型号：%10s  是否租出：%5s", key, brand, cost, maxSpeed,
                isrent));
    }

    /** 获取车辆数据 */
    @Override
    public String GetInfo() {
        return String.format("('%s', '汽车', '%s', %d, '%s', %d)", key, brand, cost, maxSpeed, isrent ? 1 : 0);
    }

    /** 设置车辆数据 */
    @Override
    public void SetInfo() {
        super.SetInfo();
        _Speak("请输入型号：");
        maxSpeed = _ReadStr();
    }

    /** 修改车辆数据 */
    @Override
    public void ChangeInfo() {
        _Speak("要修改哪一项？(1-品牌，2-租金，3-型号，4-全部)");
        int op = _ReadInt();

        switch (op) {
            case 1:
                brand = _ReadStr();
                break;
            case 2:
                cost = _ReadInt();
                break;
            case 3:
                maxSpeed = _ReadStr();
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
