//import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
//import com.microsoft.sqlserver.jdbc.SQLServerException;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class TestConnection {
//
//    public static void main(String[] args) {
//        SQLServerDataSource ds = new SQLServerDataSource();
//        ds.setUser("sa");
//        ds.setPassword("123");
//        ds.setServerName("Hello-world");
//        ds.setPortNumber(1433);
//        ds.setDatabaseName("test");
//
//        try (Connection conn = ds.getConnection()) {
//            System.out.println("Connection success");
//            System.out.println(conn.getMetaData());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
