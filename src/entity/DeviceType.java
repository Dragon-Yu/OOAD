package entity;


import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Dillion on 6/7/16.
 */
public class DeviceType extends BaseEntity {
    private String code;
    private String name;
    private String description;
    public static final String tableName = "device_type";
    private static final String saveQueryTemplate = "insert into %s (code, name, description) values ('%s', '%s', '%s')";

    public DeviceType() {
        super(tableName);
    }
    public DeviceType(String code, String name, String description) {
        this();
        this.code = code;
        this.name = name;
        this.description = description;
    }
    public DeviceType(int id, String code, String name, String description) {
        this(code, name, description);
        setId(id);
    }

    public void save() {
        super.save(String.format(saveQueryTemplate, tableName, code, name, description));
    }

    public ArrayList<Device> getDevices() {
        return Device.getDevicesByDeviceTypeId(this.getId());
    }

    public ArrayList<Plan>  getPlans() {
        return Plan.getPlansByDeviceTypeId(this.getId());
    }

    public Device addDevice(Date equipTime, String location) {
        Device device = new Device(equipTime, location, getId());
        device.save();
        return device;
    }

    public Plan addPlan(int days, String name, String description) {
        Plan plan = new Plan(days, name, description, getId());
        plan.save();
        return plan;
    }

    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        DeviceType deviceType = new DeviceType("#123A", "air mechine", "lalla");
        deviceType.save();
        deviceType.addPlan(30, "small", "small fix");
        deviceType.addPlan(50, "middle", "middle fix");
        deviceType.addPlan(90, "big", "big fix");
        deviceType.addDevice(new Date(1024), "hejian");
        deviceType.addDevice(new Date(123123123123l), "beijing");
        ArrayList<Device> deviceArrayList = deviceType.getDevices();
        for (Device device : deviceArrayList) {
            device.generatePlanSheets();
        }
        for (PlanSheet planSheet : PlanSheet.getWaitPlanSheetsWithinDays(100)) {
            System.out.println(planSheet.getId());
        }
    }
}
