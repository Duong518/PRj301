package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserDAO;


public class DeleteAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        
//        if (email == null) {
//            response.sendRedirect("login.jsp");
//            return;
//        }
        
        try {
            UserDAO userDAO = new UserDAO();
            boolean deleted = userDAO.deleteUser(email);
            
            if (deleted) {
                // Xóa session
                session.invalidate();
                
                // Chuyển hướng đến trang chủ với thông báo
                request.getSession().setAttribute("message", "Tài khoản đã được xóa thành công");
                request.getSession().setAttribute("messageType", "success");
                response.sendRedirect("home_page.html");
            } else {
                request.setAttribute("message", "Không thể xóa tài khoản");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("user_info.jsp").forward(request, response);
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi khi xóa tài khoản: " + e.getMessage());
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("user_info.jsp").forward(request, response);
        }
    }
}
