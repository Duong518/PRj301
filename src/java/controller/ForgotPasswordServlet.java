package controller;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.UserDAO;

public class ForgotPasswordServlet extends HttpServlet {

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String email = request.getParameter("email");
    String recoveryCode = request.getParameter("recoveryCode");
    String newPassword = request.getParameter("newPassword");
    String confirmPassword = request.getParameter("confirmPassword");

    try {
        UserDAO userDAO = new UserDAO();
        
        // Kiểm tra email tồn tại
        User user = userDAO.getUserByEmail(email);
        
        if (user == null) {
            request.setAttribute("error", "Email không tồn tại trong hệ thống.");
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra mã khôi phục
        if (!recoveryCode.equals(user.getRecoveryCode())) {
            request.setAttribute("error", "Mã khôi phục không đúng.");
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra mật khẩu mới và xác nhận
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
            return;
        }
        
        // Cập nhật mật khẩu mới
        boolean updated = userDAO.updatePassword(user.getId(), newPassword);
        
        if (updated) {
            request.setAttribute("message", "Mật khẩu đã được đặt lại thành công. Vui lòng đăng nhập bằng mật khẩu mới.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Không thể cập nhật mật khẩu. Vui lòng thử lại sau.");
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
        }
        
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
        request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
    }
}

    
    
    
    
    
    
    
    
    
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String email = request.getParameter("email");
//        String securityQuestion = request.getParameter("securityQuestion");
//        String securityAnswer = request.getParameter("securityAnswer");
//        String newPassword = request.getParameter("newPassword");
//        String confirmPassword = request.getParameter("confirmPassword");
//
//        try {
//            UserDAO userDAO = new UserDAO();
//            
//            // Kiểm tra email tồn tại
//            User user = userDAO.getUserByEmail(email);
//            
//            if (user == null) {
//                request.setAttribute("error", "Email không tồn tại trong hệ thống.");
//                request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
//                return;
//            }
//            
//            // Kiểm tra mật khẩu mới và xác nhận
//            if (!newPassword.equals(confirmPassword)) {
//                request.setAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
//                request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
//                return;
//            }
//            
//            // Kiểm tra câu hỏi bảo mật (nếu sử dụng)
//            if (securityQuestion != null && securityAnswer != null) {
//                boolean securityCheckPassed = userDAO.verifySecurityQuestion(email, securityQuestion, securityAnswer);
//                if (!securityCheckPassed) {
//                    request.setAttribute("error", "Câu trả lời cho câu hỏi bảo mật không đúng.");
//                    request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
//                    return;
//                }
//            }
//            
//            // Cập nhật mật khẩu mới
//            boolean updated = userDAO.updatePassword(user.getId(), newPassword);
//            
//            if (updated) {
//                request.setAttribute("message", "Mật khẩu đã được đặt lại thành công. Vui lòng đăng nhập bằng mật khẩu mới.");
//                request.getRequestDispatcher("login.jsp").forward(request, response);
//            } else {
//                request.setAttribute("error", "Không thể cập nhật mật khẩu. Vui lòng thử lại sau.");
//                request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
//            }
//            
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
//            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
//        }
//    }
}
