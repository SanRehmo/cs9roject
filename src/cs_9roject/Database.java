package cs_9roject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    /**
     * Attemps to establish a connection to the database which is then used by the DAO class
     * @return Connection
     */
    public static Connection establishConnection() {

        // attempts to load the JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        // initialization of variables
        Connection connection;

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
