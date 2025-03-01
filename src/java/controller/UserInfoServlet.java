package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.UserDAO;

public class UserInfoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserInfo(email);

            request.setAttribute("userInfo", user);
            request.getRequestDispatcher("user_info.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi khi lấy thông tin người dùng: " + e.getMessage());
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("user_info.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String fullName = request.getParameter("fullName");
        String ageStr = request.getParameter("age");
        String address = request.getParameter("address");
        String phoneStr = request.getParameter("phone");

        int age = 0;
        if (ageStr != null && !ageStr.isEmpty()) {
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Tuổi phải là số");
                request.setAttribute("messageType", "error");
                doGet(request, response);
                return;
            }
        }

        int phone = 0;
        if (phoneStr != null && !phoneStr.isEmpty()) {
            try {
                phone = Integer.parseInt(phoneStr);
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Số điện thoại phải là số");
                request.setAttribute("messageType", "error");
                doGet(request, response);
                return;
            }
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(email);

            if (user != null) {
                user.setFullName(fullName);
                user.setAge(age);
                user.setAddress(address);
                user.setPhone(phone);

                boolean updated = userDAO.updateUserInfo(user);

                if (updated) {
                    request.setAttribute("message", "Cập nhật thông tin thành công");
                    request.setAttribute("messageType", "success");
                } else {
                    request.setAttribute("message", "Không thể cập nhật thông tin");
                    request.setAttribute("messageType", "error");
                }
            } else {
                request.setAttribute("message", "Không tìm thấy thông tin người dùng");
                request.setAttribute("messageType", "error");
            }

            request.setAttribute("userInfo", user);
            request.getRequestDispatcher("user_info.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi khi cập nhật thông tin: " + e.getMessage());
            request.setAttribute("messageType", "error");
            doGet(request, response);
        }
    }

}
