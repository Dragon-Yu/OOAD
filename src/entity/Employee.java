package entity;

/**
 * Created by Dillion on 6/7/16.
 */
public class Employee extends BaseEntity {
    private String name;
    private Gender gender;
    private int age;
    public static final String tableName = "employee";
    public static final String saveQueryTemplate = "insert into %s (name, gender, age) values('%s', '%s', %d)";

    public Employee() {
        super(tableName);
    }
    public Employee(String name, Gender gender, int age) {
        this();
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
    public Employee(int id, String name, Gender gender, int age) {
        this(name, gender, age);
        this.setId(id);
    }

    public void save() {
        super.save(String.format(saveQueryTemplate, tableName, name, gender, age));
    }

    public static void main(String args[]) {
        Employee employee = new Employee("wangsan", Gender.M, 20);
        employee.save();
    }
}
