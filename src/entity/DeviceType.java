package entity;


/**
 * Created by Dillion on 6/7/16.
 */
public class DeviceType extends BaseEntity {
    private String code;
    private String name;
    private String description;
    private static final String tableName = "device_type";
    private static final String insertQueryTemplate = "insert into %s (code, name, description) values ('%s', '%s', '%s')";

    public DeviceType() {
        super(tableName);
    }

    public DeviceType(String code, String name, String description) {
        super(tableName);
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public void save() {
        super.save(String.format(insertQueryTemplate, tableName, code, name, description));
    }

    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        DeviceType planType = new DeviceType("#123A", "air mechine", "lalla");
        planType.save();
        System.out.println(planType.exist());
    }
}
