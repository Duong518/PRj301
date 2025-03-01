package model;

import dal.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Kiểm tra đăng nhập với email và mật khẩu
     */
    public boolean checkLogin(String email, String password)
            throws SQLException, ClassNotFoundException {

        // Trim input to remove any accidental spaces
        email = email.trim();
        password = password.trim();

        System.out.println("Checking login for email: " + email);

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            // Connect to database
            con = DBHelper.makeConnection();
            if (con == null) {
                System.out.println("Connection is null!");
                return false;
            }

            // First check if email exists
            if (!checkEmailExists(email)) {
                System.out.println("Email not found: " + email);
                return false;
            }

            // Now check password
            String passwordCheckSql = "SELECT * FROM users WHERE email = ? AND password = ?";
            stm = con.prepareStatement(passwordCheckSql);
            stm.setString(1, email);
            stm.setString(2, password);
            rs = stm.executeQuery();

            boolean result = rs.next();
            System.out.println("Password check result: " + result);
            return result;

        } finally {
            // Close resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
     */
    public boolean checkEmailExists(String email) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT email FROM users WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email.trim());
                rs = stm.executeQuery();

                return rs.next(); // Trả về true nếu email đã tồn tại
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return false;
    }

    /**
     * Đăng ký người dùng mới
     */
    public boolean registerUser(User user) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO users (email, password, recovery_code) VALUES (?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, user.getEmail().trim());
                stm.setString(2, user.getPassword());
                stm.setString(3, user.getRecoveryCode());

                int rowsAffected = stm.executeUpdate();
                return rowsAffected > 0;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return false;
    }

    /**
     * Lấy thông tin người dùng theo email
     */
    public User getUserByEmail(String email) throws SQLException, ClassNotFoundException {
        User user = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM users WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email.trim());
                rs = stm.executeQuery();

                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRecoveryCode(rs.getString("recovery_code"));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return user;
    }

    /**
     * Cập nhật mật khẩu mới
     */
    public boolean updatePassword(int userId, String newPassword) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE users SET password = ? WHERE id = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, newPassword);
                stm.setInt(2, userId);

                int rowsAffected = stm.executeUpdate();
                return rowsAffected > 0;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return false;
    }

    /**
     * Phương thức kiểm tra cơ sở dữ liệu
     */
    public void testDatabaseAccess() {
        try {
            Connection con = DBHelper.makeConnection();
            if (con != null) {
                System.out.println("Connected to database successfully");

                // Test query to list all users
                PreparedStatement stm = con.prepareStatement("SELECT * FROM users");
                ResultSet rs = stm.executeQuery();

                System.out.println("Users in database:");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String recoveryCode = rs.getString("recovery_code");
                    System.out.println(id + " | " + email + " | " + password + " | " + recoveryCode);
                }

                rs.close();
                stm.close();
                con.close();
            } else {
                System.out.println("Failed to connect to database");
            }
        } catch (Exception e) {
            System.out.println("Error testing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lấy thông tin đầy đủ của người dùng
    public User getUserInfo(String email) throws SQLException, ClassNotFoundException {
        User user = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM users WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email.trim());
                rs = stm.executeQuery();

                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRecoveryCode(rs.getString("recovery_code"));
                    user.setFullName(rs.getString("fullName"));
                    user.setAge(rs.getInt("age"));
                    user.setAddress(rs.getString("address"));
                    user.setPhone(rs.getInt("phone")); // Thay đổi từ getString sang getInt
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return user;
    }

// Cập nhật thông tin người dùng
    public boolean updateUserInfo(User user) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE users SET fullName = ?, age = ?, address = ?, phone = ? WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, user.getFullName());
                stm.setInt(2, user.getAge());
                stm.setString(3, user.getAddress());
                stm.setInt(4, user.getPhone()); // Thay đổi từ setString sang setInt
                stm.setString(5, user.getEmail());
                int rowsAffected = stm.executeUpdate();
                return rowsAffected > 0;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return false;
    }

// Xóa tài khoản người dùng
    public boolean deleteUser(String email) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "DELETE FROM users WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);

                int rowsAffected = stm.executeUpdate();
                return rowsAffected > 0;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return false;
    }

}
