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

    public BaseEntity(String tableName) {
        this.id = -1;
        this.tableName = tableName;
    }

    public abstract void save();

    public static Statement getDbConnection() {
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
                }
            }
        }
        return statement;
    }

    public boolean exist() {
        ResultSet result = null;
        String sql = "select count(*) from " + this.tableName + " where id = " + this.id;
        try {
            result = statement.executeQuery(sql);
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void delete() {

    }
}