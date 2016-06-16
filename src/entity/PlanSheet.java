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
    public PlanSheet(int deviceId, int planId, State state, Date shouldDoTime, Date doTime, int employeeId, float hours) {
        this();
        this.deviceId = deviceId;
        this.planId = planId;
        this.state = state;
        this.shouldDoTime = shouldDoTime;
        this.doTime = doTime;
        this.employeeId = employeeId;
        this.hours = hours;
    }
    public PlanSheet(int id, int deviceId, int planId, State state, Date shouldDoTime, Date doTime, int employeeId, float hours) {
        this(deviceId, planId, state, shouldDoTime, doTime, employeeId, hours);
        setId(id);
    }
    public PlanSheet(int deviceId, int planId, Date shouldDoTime) {
        this();
        this.deviceId = deviceId;
        this.planId = planId;
        this.shouldDoTime = shouldDoTime;
    }
    public PlanSheet(int id, int deviceId, int planId, Date shouldDoTime) {
        this(deviceId, planId, shouldDoTime);
        this.setId(id);
    }

    public Date getShouldDoTime() {
        return shouldDoTime;
    }

    private void updateFromQuery(String sql) {
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

    public static ArrayList<PlanSheet> getPlanSheetsFromQuery(String sql) {
        ArrayList<PlanSheet> planSheetArrayList = new ArrayList<PlanSheet>();
        Statement statement = MySQL.getStatementInstance();
        ResultSet result = null;
        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                int deviceId = result.getInt("device_id");
                int planId = result.getInt("plan_id");
                State state = State.valueOf(result.getString("state"));
                Date shouldDoTime = result.getDate("should_do_time");
                Date doTime = result.getDate("do_time");
                int employeeId = result.getInt("employee_id");
                float hours = result.getFloat("hours");
                planSheetArrayList.add(new PlanSheet(id, deviceId, planId, state, shouldDoTime, doTime, employeeId, hours));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return planSheetArrayList;
    }

    public static ArrayList<PlanSheet> getWaitPlanSheetsWithinDays(int days) {
        Date endDate = new Date((new java.util.Date()).getTime() + ONE_DAY * days);
        String sql = String.format(GET_WAIT_PLAN_SHEETS_WITHIN_DAYS_QUERY_TEMPLATE, TABLE_NAME, endDate, State.WAITING);
        return getPlanSheetsFromQuery(sql);
    }

    public static void main(String args[]) {
        String sql = "select * from plan_sheet where do_time is null";
        ArrayList<PlanSheet> planSheetArrayList = getPlanSheetsFromQuery(sql);
        for (PlanSheet planSheet : planSheetArrayList) {
            System.out.print(planSheet.getId() + " ");
            System.out.print(planSheet.deviceId + " ");
            System.out.print(planSheet.shouldDoTime + " ");
            System.out.print(planSheet.doTime + " ");
            System.out.print(planSheet.employeeId + " ");
            System.out.println(planSheet.hours);
        }
    }
}
