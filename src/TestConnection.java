//import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
//
//import java.sql.*;
//
//public class TestConnection {
//
//    public static void main(String[] args) {
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Test", "sa", "123456");
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
//
//            while (rs.next()) {
//                String idString = rs.getString("ID");
//                String fullnameString = rs.getString("FullName");
//
//
//                System.out.println("ID : " + idString + " | FULLNAME: " + fullnameString);
//            }
//
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
////
////        SQLServerDataSource ds = new SQLServerDataSource();
////        ds.setUser("sa");
////        ds.setPassword("123456");
////        ds.setServerName("Hello-world");
////        ds.setPortNumber(1433);
////        ds.setDatabaseName("Test");
////
////        try (Connection conn = ds.getConnection()) {
////            System.out.println("Connection success");
////            System.out.println(conn.getMetaData());
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
//    }
//}
