package entity;

/**
 * Created by Dillion on 6/7/16.
 */
public class Plan extends BaseEntity{
    private int days;
    private String name;
    private String description;
    private String deviceTpyeId;
    private static final String tableName = "plan";
    private static final String insertQueryTemplate = "insert into %s (days, name, description, device_type_id) values(%d, '%s', '%s', '%s')";

    public Plan() {
        super(tableName);
    }

    public Plan(int days, String name, String description, String deviceTpyeId) {
        super(tableName);
        this.days = days;
        this.name = name;
        this.description = description;
        this.deviceTpyeId = deviceTpyeId;
    }

    public Plan(int id, int days, String name, String description, String deviceTpyeId) {
        super(id, tableName);
        this.days = days;
        this.name = name;
        this.description = description;
        this.deviceTpyeId = deviceTpyeId;
    }

    public void save() {
        super.save(String.format(insertQueryTemplate, days, name, description, deviceTpyeId));
    }

    public static void main(String args[]) {
    }

}