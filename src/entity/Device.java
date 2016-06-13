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
public class Device extends BaseEntity {
    private Date equipTime;
    private String location;
    private int deviceTypeId;
    public static final String TABLE_NAME = "device";
    private static final String SAVE_QUERY_TEMPLATE = "insert into %s (equip_time, location, device_type_id) values ('%s', '%s', %d)";
    private static final String GET_DEVICES_BY_DEVICE_TYPE_ID_QUERY_TEMPLATE = "select * from %s where device_type_id = %d";
    private static final String GET_TOTAL_HOURS_QUERY_TEMPLATE = "select sum(hours) as sum_hours from %s where device_id = %d and state = '%s'";
    private static final String GET_TOTAL_HOURS_BY_PLAN_NAME_QUERY_TEMPLATE = "select sum(a.hours) as sum_hours from %s as a,%s as b where a.plan_id = b.id and a.device_id = %d and a.state = '%s' and b.name = '%s'";

    public Device() {
        super(TABLE_NAME);
    }
    public Device(Date equipTime, String location, int deviceTypeId) {
        this();
        this.equipTime = equipTime;
        this.location = location;
        this.deviceTypeId = deviceTypeId;
    }
    private Device(int id, Date equipTime, String location, int deviceTypeId) {
        this(equipTime, location, deviceTypeId);
        setId(id);
    }

    public void save() {
        super.save(String.format(SAVE_QUERY_TEMPLATE, Device.TABLE_NAME, equipTime, location, deviceTypeId));
    }

    public ArrayList<PlanSheet> generatePlanSheets() {
        ArrayList<PlanSheet> planSheetArrayList = new ArrayList<PlanSheet>();
        ArrayList<Plan> planArrayList = Plan.getPlansByDeviceTypeId(deviceTypeId);
        Date startDate = new Date(new java.util.Date().getTime());
        for (Plan plan : planArrayList) {
            Date nowDate = new Date(new java.util.Date().getTime());
            for (int i = plan.getDays(); i <= 365; i += plan.getDays()) {
                nowDate.setTime(nowDate.getTime() + plan.getDays() * ONE_DAY);
                PlanSheet planSheet = new PlanSheet(getId(), plan.getId(), nowDate);
                planSheet.save();
                planSheetArrayList.add(planSheet);
            }
        }
        return planSheetArrayList;
    }

    public float getTotalHoursFromQuery(String sql) {
        float totalHours = 0;
        ResultSet result = null;
        try {
            Statement statement = MySQL.getStatementInstance();
            result = statement.executeQuery(sql);
            if (result.next()) {
                totalHours = result.getFloat("sum_hours");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return totalHours;
    }

    public float getTotalHours() {
        String sql = String.format(GET_TOTAL_HOURS_QUERY_TEMPLATE, PlanSheet.TABLE_NAME, Plan.tableName, getId(), State.FINISHED);
        return getTotalHoursFromQuery(sql);
    }

    public float getTotalHoursByPlanName(PlanName planName) {
        String sql = String.format(GET_TOTAL_HOURS_BY_PLAN_NAME_QUERY_TEMPLATE, PlanSheet.TABLE_NAME, Plan.tableName, getId(), State.FINISHED, planName);
        return getTotalHoursFromQuery(sql);
    }

    public static ArrayList<Device> getDevicesFromQuery(String sql) {
        ArrayList<Device> deviceArrayList = new ArrayList<Device>();
        ResultSet result = null;
        try {
            Statement statement = MySQL.getStatementInstance();
            result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                Date equipTime = result.getDate("equip_time");
                String location = result.getString("location");
                int deviceTypeId = result.getInt("device_type_id");
                deviceArrayList.add(new Device(id, equipTime, location, deviceTypeId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return deviceArrayList;
    }

    public static ArrayList<Device> getDevicesByDeviceTypeId(int deviceTypeId) {
        String sql = String.format(GET_DEVICES_BY_DEVICE_TYPE_ID_QUERY_TEMPLATE, Device.TABLE_NAME, deviceTypeId);
        ArrayList<Device> deviceArrayList = getDevicesFromQuery(sql);
        return deviceArrayList;
    }
}
