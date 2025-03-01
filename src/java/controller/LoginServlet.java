    package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.UserDAO;


public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String INVALID_PAGE = "login.jsp";
    private final String HOME_PAGE = "home_page_login.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(INVALID_PAGE).forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = INVALID_PAGE;
        
        try {
            // Get login parameters
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            // Debug info
            System.out.println("Login attempt - Email: " + email);
            
            // Validate input
            if (email == null || email.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Email và mật khẩu không được để trống");
                request.getRequestDispatcher(INVALID_PAGE).forward(request, response);
                return;
            }
            
            // Test database connection first
            UserDAO dao = new UserDAO();
            dao.testDatabaseAccess();
            
            // Check login
            boolean loginSuccess = dao.checkLogin(email, password);
            
            if (loginSuccess) {
                // Login successful
                User user = dao.getUserByEmail(email);
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("email", email);
                
                // Redirect to home page
                url = HOME_PAGE;
                response.sendRedirect(url);
            } else {
                // Login failed
                request.setAttribute("errorMessage", "Email hoặc mật khẩu không đúng");
                request.getRequestDispatcher(INVALID_PAGE).forward(request, response);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu: " + ex.getMessage());
            request.getRequestDispatcher(INVALID_PAGE).forward(request, response);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Không tìm thấy driver JDBC: " + ex.getMessage());
            request.getRequestDispatcher(INVALID_PAGE).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống: " + ex.getMessage());
            request.getRequestDispatcher(INVALID_PAGE).forward(request, response);
        } finally {
            out.close();
        }
    }
}
