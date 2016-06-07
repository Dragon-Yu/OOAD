package entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Dillion on 6/7/16.
 */
public class PlanSheet extends BaseEntity {
    private int deviceId;
    private int planId;
    private State state;
    private Date shouldDoTime;
    private Date doTime;
    private int employeeId;
    private float hours;
    public static String tableName = "plan_sheet";
    private static final String saveQueryTemplate = "insert into %s (device_id, plan_id, should_do_time) values(%d, %d, '%s')";
    private static final String finishQueryTemplate = "update %s set state = '%s', do_time = '%s', employee_id = %d, hours = %f";
    private static final String cancelQueryTemplate = "update %s set state = '%s'";
    private static final String getWaitPlanSheetsWithinDaysQueryTemplate = "select * from %s where should_do_time < '%s' and state = '%s'";

    public PlanSheet() {
        super(tableName);
    }
    public PlanSheet(int deviceId, int planId, Date shouldDoTime) {
        this();
        this.deviceId = deviceId;
        this.planId = planId;
        this.shouldDoTime = shouldDoTime;
    }
    public PlanSheet(int id, int deviceId, int planId, Date shouldDoTime) {
        this(deviceId, planId, shouldDoTime);
        setId(id);
    }

    public void updateFromQuery(String sql) {
        Statement statement = getStatementInstance();
        try {
            int result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void finish(Date doTime, int employeeId, float hours) {
        updateFromQuery(String.format(finishQueryTemplate, tableName, State.finished, doTime, employeeId, hours));
    }

    public void cancel() {
        updateFromQuery(String.format(cancelQueryTemplate, tableName));
    }

    public void save() {
        super.save(String.format(saveQueryTemplate, PlanSheet.tableName, deviceId, planId, shouldDoTime));
    }

    public static ArrayList<PlanSheet> getWaitPlanSheetsWithinDays(int days) {
        ArrayList<PlanSheet> planSheetArrayList = new ArrayList<PlanSheet>();
        Date endDate = new Date((new java.util.Date()).getTime() + oneDay * days);
        String sql = String.format(getWaitPlanSheetsWithinDaysQueryTemplate, tableName, endDate, State.wait);
        Statement statement = getStatementInstance();
        ResultSet result = null;
        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                int deviceId = result.getInt("device_id");
                int planId = result.getInt("plan_id");
                Date shouldDoTime = result.getDate("should_do_time");
                planSheetArrayList.add(new PlanSheet(id, deviceId, planId, shouldDoTime));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return planSheetArrayList;
    }
}
