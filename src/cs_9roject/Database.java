package cs_9roject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by krischan on 24.04.17.
 */
public class Database {

    public static Connection establishConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        Connection connection = null;
        Statement stmt = null;

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/bedrock?useSSL=false", "root", "cs9roject");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return connection;
    }
}
