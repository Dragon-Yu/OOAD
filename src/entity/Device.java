package entity;

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
    private static final int oneDay = 3600 * 1000 * 24;
    public static final String tableName = "device";
    private static final String saveQueryTemplate = "insert into %s (equip_time, location, device_type_id) values ('%s', '%s', %d)";
    private static final String getDevicesByDeviceTypeIdQueryTemplate = "select * from %s where device_type_id = %d";

    public Device() {
        super(tableName);
    }
    public Device(Date equipTime, String location, int deviceTypeId) {
        this();
        this.equipTime = equipTime;
        this.location = location;
        this.deviceTypeId = deviceTypeId;
    }
    public Device(int id, Date equipTime, String location, int deviceTypeId) {
        this(equipTime, location, deviceTypeId);
        setId(id);
    }

    public void save() {
        super.save(String.format(saveQueryTemplate, Device.tableName, equipTime, location, deviceTypeId));
    }

    public ArrayList<PlanSheet> generatePlanSheets() {
        ArrayList<PlanSheet> planSheetArrayList = new ArrayList<PlanSheet>();
        ArrayList<Plan> planArrayList = Plan.getPlansByDeviceTypeId(deviceTypeId);
        Date startDate = new Date(new java.util.Date().getTime());
        for (Plan plan : planArrayList) {
            Date nowDate = new Date(new java.util.Date().getTime());
            for (int i = plan.getDays(); i <= 365; i += plan.getDays()) {
                nowDate.setTime(nowDate.getTime() + plan.getDays() * oneDay);
                PlanSheet planSheet = new PlanSheet(getId(), plan.getId(), nowDate);
                planSheet.save();
                planSheetArrayList.add(planSheet);
            }
        }
        return planSheetArrayList;
    }

    public static ArrayList<Device> getDevicesFromQuery(String sql) {
        ArrayList<Device> deviceArrayList = new ArrayList<Device>();
        ResultSet result = null;
        try {
            Statement statement = getStatementInstance();
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
        String sql = String.format(getDevicesByDeviceTypeIdQueryTemplate, Device.tableName, deviceTypeId);
        ArrayList<Device> deviceArrayList = getDevicesFromQuery(sql);
        return deviceArrayList;
    }
}
