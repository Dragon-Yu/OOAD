package entity;

/**
 * Created by Dillion on 6/7/16.
 */
public class Plan extends BaseEntity{
    private int days;
    private String name;
    private String description;
    private String deviceTpyeCode;
    private static String tableName = "plan";

    public Plan() {
        super(tableName);
    }

    public Plan(int days, String name, String description, String deviceTpyeCode) {
        super(tableName);
        this.days = days;
        this.name = name;
        this.description = description;
        this.deviceTpyeCode = deviceTpyeCode;
    }

    public boolean ifExist() {
        String sql = "select count(*) from ";
        return false;
    }

    public void save() {

    }

    public void delete() {

    }

}
