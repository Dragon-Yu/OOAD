package entity;

/**
 * Created by Dillion on 6/7/16.
 */
public class Employee extends BaseEntity {
    private String name;
    private Gender gender;
    private int age;
    public static final String TABLE_NAME = "employee";
    public static final String SAVE_QUERY_TEMPLATE = "insert into %s (name, gender, age) values('%s', '%s', %d)";

    public Employee() {
        super(TABLE_NAME);
    }
    public Employee(String name, Gender gender, int age) {
        this();
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
    private Employee(int id, String name, Gender gender, int age) {
        this(name, gender, age);
        this.setId(id);
    }

    public void save() {
        super.save(String.format(SAVE_QUERY_TEMPLATE, TABLE_NAME, name, gender, age));
    }

    public static void main(String args[]) {
        Employee employee = new Employee("wangsan", Gender.M, 20);
        employee.save();
    }
}
