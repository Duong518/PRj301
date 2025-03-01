package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=test;trustServerCertificate=true";
    private static final String USER = "Duong";
    private static final String PASSWORD = "1234";

    public static Connection makeConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            // Load JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công đến database!");
            
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver không tìm thấy: " + e.getMessage());
            throw e;
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối SQL: " + e.getMessage());
            throw e;
        }
    }
    
    // Test connection method
    public static boolean testConnection() {
        try {
            Connection conn = makeConnection();
            if (conn != null) {
                System.out.println("Kết nối test thành công!");
                conn.close();
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi test kết nối: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
