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
    public static final String TABLE_NAME = "device_type";
    private static final String SAVE_QUERY_TEMPLATE = "insert into %s (code, name, description) values ('%s', '%s', '%s')";

    public DeviceType() {
        super(TABLE_NAME);
    }
    public DeviceType(String code, String name, String description) {
        this();
        this.code = code;
        this.name = name;
        this.description = description;
    }

    private DeviceType(int id, String code, String name, String description) {
        this(code, name, description);
        setId(id);
    }

    public void save() {
        super.save(String.format(SAVE_QUERY_TEMPLATE, TABLE_NAME, code, name, description));
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
