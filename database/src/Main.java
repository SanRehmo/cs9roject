/**
 * Created by krischan on 30.03.17.
 */

import java.sql.*;


public class Main {

    public static void main(String[] args) {


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Connection connection = null;
        Statement stmt = null;

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/bedrock","root", "cs9roject");

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            String query = "SELECT * FROM employee WHERE jobTitle=\"Neighbor\"";
            try {
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("All Neighbors in the database:");
                while(rs.next()) {
                    System.out.println(rs.getString("Name"));
                }
            } catch (Exception ex) {
                System.out.println("EX");
            }
        } else {
            System.out.println("Failed to make connection");
        }
    }
}
