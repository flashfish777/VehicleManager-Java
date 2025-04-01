package RentRecord;

import java.sql.Date;
import IO.IO;
import Managers.UsersManager;
import Managers.VehicleManager;

/*
 * 租借记录
 */
public class RentRecord extends IO {
    private int rentID;
    private String vehicleNo;
    private String userName;
    private Date time;
    private boolean isReturn;
    private Long getMoney;
    private String Name;

    public RentRecord(int rentID, String vehicleNo, String userName, Date time, boolean isReturn, long getMoney,
            String Name) {
        this.rentID = rentID;
        this.vehicleNo = vehicleNo;
        this.userName = userName;
        this.time = time;
        this.isReturn = isReturn;
        this.getMoney = getMoney;
        this.Name = Name;
    }

    /* 打印(merchant) */
    public void MPrint() {
        _Speak(String.format("编号：%03d  车牌号：%9s  用户：%10s  时间：%20s  是否归还：%6s  支付金额：%d", rentID, vehicleNo, Name, time.toString(),
                isReturn, getMoney));
    }

    /* 打印(user) */
    public void UPrint(String userName) {
        if (this.userName.equals(userName) && !isReturn) {
            _Speak(String.format("车牌号：%9s  开始时间：%s", vehicleNo, time.toString()));
        }
    }

    /* 还车 */
    public long ReturnVehicle(String vehicleNo) {
        if (this.vehicleNo.equals(vehicleNo) && !isReturn) {
            Long now = new java.util.Date().getTime();
            int days = (int) ((now - time.getTime()) / 1000 / 3600 / 24) + 1;
            long pay = days * VehicleManager.Instance.GetVehicle(vehicleNo).getCost();

            if (UsersManager.Instance.getNowUser().getMoney() < pay) {
                _Speak("还车失败！您共借车" + days + "天（不足1天按1天计算），需要支付" + pay + "元，您的余额不足！");
                return 0;
            }

            isReturn = true;
            getMoney = pay;
            UsersManager.Instance.getNowUser().setMoney(pay);
            VehicleManager.Instance.GetVehicle(vehicleNo).setIsrent(false);
            _Speak("归还成功！您共借车" + days + "天（不足1天按1天计算），已自动支付" + pay + "元");

            return pay;
        }

        return -1;
    }

    /* 存储 */
    public String GetInfo() {
        return String.format("('%s', '%s', '%s', %d, %d)", vehicleNo, userName, time.toString(),
                isReturn ? 1 : 0,
                getMoney);
    }

    @Override
    public boolean equals(Object obj) {
        RentRecord rent = (RentRecord) obj;
        return rent.userName.equals(userName) && isReturn == false && rent.isReturn == false ? true : false;
    }
}
