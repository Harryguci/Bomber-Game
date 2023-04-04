package bin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {
    public static Connection connect() {
        Connection connection = null;
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=your_database";
            String username = "your_username";
            String password = "your_password";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to establish connection.");
            e.printStackTrace();
        }

        return connection;
    }

    public static void getData(String sql, String username, String password) {
        Connection connection = null;
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=your_database";
//            String username = "your_username";
//            String password = "your_password";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to establish connection.");
            e.printStackTrace();
        }

//      String sql = "SELECT * FROM your_table";

        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                // process each row in the result set
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
