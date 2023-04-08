package bin;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSQLTest {
    private String username;
    private String password;
    private int level;
    private int maxScore;

    public UserSQLTest(String username, String password, int level, int maxScore) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.maxScore = maxScore;
    }

    // Getter and Setter methods for each attribute

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    // Method to retrieve user information from the database

    public static UserSQLTest getUser(String username, String password) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=myDatabase";
        String user = "myUsername";
        String pass = "myPassword";

        UserSQLTest userObj = null;

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int level = rs.getInt("level");
                int maxScore = rs.getInt("max_score");
                userObj = new UserSQLTest(username, password, level, maxScore);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userObj;
    }
}
