package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.UserDAO;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Thiết lập encoding để xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // Nhận dữ liệu từ form
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String recoveryCode = request.getParameter("recoveryCode");
        
        // Kiểm tra tính hợp lệ của dữ liệu
        if (!isValidInput(email, password, confirmPassword, recoveryCode, request)) {
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        try {
            UserDAO userDAO = new UserDAO();
            
            // Kiểm tra email đã tồn tại chưa
            if (userDAO.checkEmailExists(email)) {
                request.setAttribute("errorMessage", "Email này đã được đăng ký. Vui lòng sử dụng email khác.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            
            // Tạo đối tượng User mới
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setRecoveryCode(recoveryCode);
            
            // Thêm người dùng mới vào cơ sở dữ liệu
            boolean registrationSuccess = userDAO.registerUser(newUser);
            
            if (registrationSuccess) {
                // Đăng ký thành công, chuyển hướng đến trang đăng nhập với thông báo thành công
                request.getSession().setAttribute("registrationSuccess", "Đăng ký thành công! Vui lòng đăng nhập.");
                response.sendRedirect("login.jsp");
            } else {
                // Đăng ký thất bại do lỗi cơ sở dữ liệu
                request.setAttribute("errorMessage", "Đăng ký thất bại. Vui lòng thử lại sau.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            
        } catch (SQLException e) {
            // Xử lý lỗi SQL
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
            // Xử lý lỗi không tìm thấy driver JDBC
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } catch (Exception e) {
            // Xử lý các lỗi khác
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi không xác định: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
    
    /**
     * Kiểm tra tính hợp lệ của dữ liệu đầu vào
     * @return true nếu dữ liệu hợp lệ, false nếu không
     */
    private boolean isValidInput(String email, String password, String confirmPassword, 
                                String recoveryCode, HttpServletRequest request) {
        // Kiểm tra email
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email không được để trống");
            return false;
        }
        
        // Kiểm tra định dạng email
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(emailRegex)) {
            request.setAttribute("errorMessage", "Email không hợp lệ");
            return false;
        }
        
        // Kiểm tra mật khẩu
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Mật khẩu không được để trống");
            return false;
        }
        
        // Kiểm tra độ dài mật khẩu
        if (password.length() < 6) {
            request.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }
        
        // Kiểm tra xác nhận mật khẩu
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp");
            return false;
        }
        
        // Kiểm tra mã khôi phục
        if (recoveryCode == null || recoveryCode.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Mã khôi phục không được để trống");
            return false;
        }
        
        return true; // Dữ liệu hợp lệ
    }
}
