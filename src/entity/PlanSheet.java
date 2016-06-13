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
public class PlanSheet extends BaseEntity {
    private int deviceId;
    private int planId;
    private State state;
    private Date shouldDoTime;
    private Date doTime;
    private int employeeId;
    private float hours;
    public static final String TABLE_NAME = "plan_sheet";
    private static final String SAVE_QUERY_TEMPLATE = "insert into %s (device_id, plan_id, should_do_time) values(%d, %d, '%s')";
    private static final String FINISH_QUERY_TEMPLATE = "update %s set state = '%s', do_time = '%s', employee_id = %d, hours = %f where id = %d";
    private static final String CANCEL_QUERY_TEMPLATE = "update %s set state = '%s' where id = %d";
    private static final String GET_WAIT_PLAN_SHEETS_WITHIN_DAYS_QUERY_TEMPLATE = "select * from %s where should_do_time < '%s' and state = '%s'";

    public PlanSheet() {
        super(TABLE_NAME);
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

    public Date getShouldDoTime() {
        return shouldDoTime;
    }

    public void updateFromQuery(String sql) {
        Statement statement = MySQL.getStatementInstance();
        try {
            int result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void finish(Date doTime, int employeeId, float hours) {
        updateFromQuery(String.format(FINISH_QUERY_TEMPLATE, TABLE_NAME, State.FINISHED, doTime, employeeId, hours, getId()));
    }

    public void cancel() {
        updateFromQuery(String.format(CANCEL_QUERY_TEMPLATE, TABLE_NAME, State.CANCELED, getId()));
    }

    public void save() {
        super.save(String.format(SAVE_QUERY_TEMPLATE, PlanSheet.TABLE_NAME, deviceId, planId, shouldDoTime));
    }

    public static ArrayList<PlanSheet> getWaitPlanSheetsWithinDays(int days) {
        ArrayList<PlanSheet> planSheetArrayList = new ArrayList<PlanSheet>();
        Date endDate = new Date((new java.util.Date()).getTime() + ONE_DAY * days);
        String sql = String.format(GET_WAIT_PLAN_SHEETS_WITHIN_DAYS_QUERY_TEMPLATE, TABLE_NAME, endDate, State.WAITING);
        Statement statement = MySQL.getStatementInstance();
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
