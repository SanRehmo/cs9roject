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

        // attempts to load the JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        // initialization of variables
        Connection connection = null;
        Statement stmt = null;

        // attempt to connect to our database with our credentials
        try {
            connection = DriverManager
            		.getConnection("jdbc:mysql://77.105.202.3:3306/bedrock?useSSL=false", "root", "password");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // return the connection object
        return connection;
    }
}
