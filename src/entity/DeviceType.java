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
        return Device.getDevicesByDeviceTypeId(getId());
    }

    public ArrayList<Plan>  getPlans() {
        return Plan.getPlansByDeviceTypeId(getId());
    }

    public Device addDevice(Date equipTime, String location) {
        Device device = new Device(equipTime, location, getId());
        device.save();
        return device;
    }

    public Plan addPlan(int days, PlanName name, String description) {
        Plan plan = new Plan(days, name, description, getId());
        plan.save();
        return plan;
    }
}
