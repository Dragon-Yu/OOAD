package entity;

import java.sql.*;

/**
 * Created by Dillion on 6/7/16.
 */
public abstract class BaseEntity {
    private static final String dbUrl = "jdbc:mysql://localhost:3307/device_fixing_management?" +
            "user=root&password=3223232mysql";
    private static Connection conn = null;
    private static Statement statement = null;
    private int id;
    private String tableName;
    private static final String deleteQueryTemplate = "delete from %s where id = %d";

    public BaseEntity(String tableName) {
        this.id = -1;
        this.tableName = tableName;
    }

    public abstract void save();

    public static Statement getStatementInstance() {
        if (statement == null){
            synchronized (BaseEntity.class){
                if (statement == null) {
                    try {
                        conn = DriverManager.getConnection(dbUrl);
                        statement = conn.createStatement();
                        return statement;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return statement;
                }
            }
        } else {
            return statement;
        }
    }

    public boolean exist() {
        ResultSet result = null;
        String sql = "select count(*) from " + tableName + " where id = " + id;
        try {
            result = getStatementInstance().executeQuery(sql);
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void delete() {
        String sql = String.format(deleteQueryTemplate, tableName, id);
        try {
            int result = getStatementInstance().executeUpdate(sql);
            System.out.println(result + " rows affected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(String sql) {
        if (exist()) {
            delete();
        }
        try {
            int result = getStatementInstance().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}