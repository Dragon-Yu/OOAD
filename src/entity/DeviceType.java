package entity;


import database.MySQL;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public static ArrayList<DeviceType> getDeviceTypesFromQuery(String sql) {
        ArrayList<DeviceType> deviceTypeArrayList = new ArrayList<DeviceType>();
        ResultSet result;
        try {
            Statement statement = MySQL.getStatementInstance();
            result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String code = result.getString("code");
                String name = result.getString("name");
                String description = result.getString("description");
                deviceTypeArrayList.add(new DeviceType(id, code, name, description));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return deviceTypeArrayList;
    }

    public void save() {
        super.save(String.format(SAVE_QUERY_TEMPLATE, TABLE_NAME, code, name, description));
    }

    public void addDeviceType(){
        this.save();
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

    public Plan addPlan(int days, PlanType type, String description) {
        Plan plan = new Plan(days, type, description, getId());
        plan.save();
        return plan;
    }

    public static void main(String args[]) {
        String sql = "select * from device_type where code = '#A2'";
        ArrayList<DeviceType> deviceTypeArrayList = DeviceType.getDeviceTypesFromQuery(sql);
        for (DeviceType deviceType : deviceTypeArrayList) {
            System.out.print(deviceType.getId() + " ");
            System.out.print(deviceType.code + " ");
            System.out.print(deviceType.name + " ");
            System.out.println(deviceType.description + " ");
        }
    }
}
