package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dillion on 6/13/16.
 */
public class MySQL {
//    private static final String DB_URL = "jdbc:mysql://localhost:3307/device_fixing_management?" +
//            "user=root&password=3223232mysql";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/device_fixing_management?" +
            "user=root&password=yxl";
    private static Connection conn = null;
    private static Statement statement = null;

    public static synchronized Statement getStatementInstance() {
        if (statement == null) {
            try {
                conn = DriverManager.getConnection(DB_URL);
                statement = conn.createStatement();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Connection Error With MySQL Server, Program Is Exiting");
                System.exit(-1);
            }
        }
        return statement;
    }
}
