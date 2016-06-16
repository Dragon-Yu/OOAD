package entity;

import database.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Dillion on 6/7/16.
 */
public abstract class BaseEntity {
    private int id;
    private final String tableName;
    public static final long ONE_DAY = 3600 * 1000 * 24;
    private static final String EXIST_QUERY_TEMPLATE = "select count(*) from %s where id = %d";
    private static final String DELETE_QUERY_TEMPLATE = "delete from %s where id = %d";
    private static final String GET_NEW_ID_QUERY_TEMPLATE = "select max(id) as max_id from %s";

    public BaseEntity(String tableName) {
        this.id = -1;
        this.tableName = tableName;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public boolean exist() {
        String sql = String.format(EXIST_QUERY_TEMPLATE, tableName, id);
        ResultSet result = null;
        try {
            result = MySQL.getStatementInstance().executeQuery(sql);
            result.next();
            if (result.getInt(1) <= 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void delete() {
        String sql = String.format(DELETE_QUERY_TEMPLATE, tableName, id);
        try {
            int result = MySQL.getStatementInstance().executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(String sql) {
        ResultSet result = null;
        if (exist()) {
            delete();
        }
        try {
            MySQL.getStatementInstance().executeUpdate(sql);
            result = MySQL.getStatementInstance().executeQuery(String.format(GET_NEW_ID_QUERY_TEMPLATE, tableName));
            result.next();
            id = result.getInt("max_id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}