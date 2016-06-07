package entity;

import java.sql.Date;

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
    public static final String saveQueryTemplate = "insert into %s (device_id, plan_id, should_do_time) values(%d, %d, '%s')";

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

    public void save() {
        super.save(String.format(saveQueryTemplate, PlanSheet.tableName, deviceId, planId, state, employeeId, hours));
    }
}
