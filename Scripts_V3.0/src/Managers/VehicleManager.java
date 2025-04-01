package Managers;

import java.util.List;
import IO.IO;
import Vehicle.*;

/**
 * 车辆管理器
 */
public class VehicleManager extends IO {
    /** 车辆管理器实例 */
    public static VehicleManager Instance;

    /** 车辆列表 */
    private List<VehicleBase> vehicle;

    /** 创建实例 */
    public static void Init() throws Exception {
        Instance = new VehicleManager();
    }

    /** 初始化 */
    public VehicleManager() throws Exception {
        vehicle = DataManager.Instance.ReadVehicleFromSQL();
    }

    /** 查看所有车辆 */
    public void CheckAll() {
        for (VehicleBase veh : vehicle) {
            veh.Print();
        }
    }

    /** 添加车辆 */
    public void AddVehicle() {
        VehicleBase opVehicle;
        _Speak("\n请输入车牌号：");
        String No = _ReadStr();

        if (vehicle.contains(new Car(No))) {
            _Speak("该车辆已存在！");
            return;
        }

        _Speak("请输入车辆类型（1-汽车，2-客车，3-货车）：");
        int type = _ReadInt();

        switch (type) {
            case 1:
                opVehicle = new Car(No);
                break;
            case 2:
                opVehicle = new Bus(No);
                break;
            case 3:
                opVehicle = new Trunk(No);
                break;
            default:
                System.out.println("没有该车辆类型！");
                return;
        }
        opVehicle.SetInfo();
        vehicle.add(opVehicle);
        _Speak("添加成功！");
    }

    /** 修改车辆信息 */
    public void OverrideVehicle() {
        _Speak("\n请输入要修改车辆的车牌号：");
        String key = _ReadStr();

        int index = vehicle.indexOf(new Car(key));
        if (index == -1) {
            _Speak("该车辆不存在！");
            return;
        } else {
            vehicle.get(index).Print();
            vehicle.get(index).ChangeInfo();
            _Speak("修改成功！");
        }
    }

    /** 删除车辆 */
    public void DeleteVehicle() {
        _Speak("\n请输入要删除车辆的车牌号：");
        String key = _ReadStr();

        int index = vehicle.indexOf(new Car(key));
        if (index == -1) {
            _Speak("该车辆不存在");
            return;
        } else {
            vehicle.remove(index);
            _Speak("删除成功");
        }
    }

    /** 获取车辆信息 */
    public VehicleBase GetVehicle(String No) {
        int index = vehicle.indexOf(new Car(No));
        if (index == -1) {
            return null;
        }
        return vehicle.get(index);
    }

    /** 保存数据 */
    public List<VehicleBase> LoadVehicle() throws Exception {
        // DataManager.Instance.LoadVehicleToSQL(vehicle);
        return vehicle;
    }
}
