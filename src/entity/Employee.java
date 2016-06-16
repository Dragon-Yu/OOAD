package entity;

import database.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    public static ArrayList<Employee> getEmployeeFromQuery(String sql) {
        ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();
        ResultSet result;
        try {
            Statement statement = MySQL.getStatementInstance();
            result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                Gender gender = Gender.valueOf(result.getString("gender"));
                int age = result.getInt("age");
                employeeArrayList.add(new Employee(id, name, gender, age));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employeeArrayList;
    }

    public void save() {
        super.save(String.format(SAVE_QUERY_TEMPLATE, TABLE_NAME, name, gender, age));
    }

    public static void main(String args[]) {
        String sql = "select * from employee where id = 2";
        ArrayList<Employee> employeeArrayList = Employee.getEmployeeFromQuery(sql);
        for (Employee employee : employeeArrayList) {
            System.out.print(employee.getId() + " ");
            System.out.print(employee.name + " ");
            System.out.print(employee.gender + " ");
            System.out.println(employee.age);
        }
    }
}
