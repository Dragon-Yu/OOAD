package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Dillion on 6/7/16.
 */
public class Plan extends BaseEntity{
    private int days;
    private String name;
    private String description;
    private int deviceTpyeId;
    public static final String tableName = "plan";
    private static final String saveQueryTemplate = "insert into %s (days, name, description, device_type_id) values(%d, '%s', '%s', %d)";
    private static final String getPlansByDeviceTypeIdQueryTemplate = "select * from %s where device_type_id = %d";
    private static final String getPlansByIdQueryTemplate = "select * from %s where id = %d";

    public Plan() {
        super(tableName);
    }

    public Plan(int days, String name, String description, int deviceTpyeId) {
        this();
        this.days = days;
        this.name = name;
        this.description = description;
        this.deviceTpyeId = deviceTpyeId;
    }
    public Plan(int id, int days, String name, String description, int deviceTpyeId) {
        this(days, name, description, deviceTpyeId);
        setId(id);
    }

    public void save() {
        super.save(String.format(saveQueryTemplate, Plan.tableName,  days, name, description, deviceTpyeId));
    }

    public static ArrayList<Plan> getPlansFromQuery(String sql) {
        ArrayList<Plan> planArrayList = new ArrayList<Plan>();
        ResultSet result = null;
        try {
            Statement statement = getStatementInstance();
            result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                int days = result.getInt("days");
                String name = result.getString("name");
                String description = result.getString("description");
                int deviceTypeId = result.getInt("device_type_id");
                planArrayList.add(new Plan(id, days, name, description, deviceTypeId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return planArrayList;
    }

    public static ArrayList<Plan> getPlansByDeviceTypeId(int deviceTypeId) {
        String sql = String.format(getPlansByDeviceTypeIdQueryTemplate, Plan.tableName, deviceTypeId);
        ArrayList<Plan> planArrayList = getPlansFromQuery(sql);
        return planArrayList;
    }

    public static ArrayList<Plan> getPlansById(int id) {
        String sql = String.format(getPlansByIdQueryTemplate, Plan.tableName, id);
        ArrayList<Plan> planArrayList = getPlansFromQuery(sql);
        return planArrayList;
    }

    public int getDays() {
        return days;
    }

    public static void main(String args[]) {
    }

}