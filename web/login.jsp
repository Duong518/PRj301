<%-- 
    Document   : login
    Created on : Feb 28, 2025, 7:55:02 AM
    Author     : Admin
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi" data-theme="light">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng nhập - Chia Sẻ Trang Web</title>
        <style>
            /* THEME VARIABLES - Dễ dàng tùy chỉnh theme */
            :root {
                --background-color: #f5f5f5;
                --container-border: #000;
                --navbar-bg: #fff;
                --text-color: #333;
                --card-bg: #fff;
                --header-gradient-start: #8B5E3C;
                --header-gradient-end: #5A3A21;
                --header-text: #fff;
                --input-border: #ddd;
                --button-hover: #5A3A21;
                --error-color: #e74c3c;
            }

            [data-theme="dark"] {
                --background-color: #222;
                --container-border: #444;
                --navbar-bg: #333;
                --text-color: #eee;
                --card-bg: #444;
                --header-gradient-start: #5A3A21;
                --header-gradient-end: #3A2010;
                --header-text: #fff;
                --input-border: #555;
                --button-hover: #3A2010;
                --error-color: #e74c3c;
            }

            /* BASE STYLES */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Arial, sans-serif;
            }

            body {
                background-color: var(--background-color);
                color: var(--text-color);
                transition: all 0.3s ease;
            }

            .container {
                width: 100%;
                max-width: 1400px;
                margin: 0 auto;
                border: 1px solid var(--container-border);
                min-height: 100vh;
                display: flex;
                flex-direction: column;
                background-color: #FFFACD; /* Màu be nhạt cho container */
            }

            /* HEADER STYLES */

            header {
                background-color: #FFFACD; /* Màu be nhạt cho container */
            }

            .navbar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 20px;
                flex-wrap: wrap;
            }

            .nav-item {
                padding: 8px 15px;
                margin: 5px;
                cursor: pointer;
            }

            .logo {
                font-weight: bold;
                font-size: 1.5rem;
            }

            .nav-item a {
                text-decoration: none;
                color: var(--text-color);
            }

            .category-header {
                background-color: #654321; /* Màu nâu cho header */
                color: #FFFF00; /* Chữ màu vàng */
            }
            /* BANNER STYLES */
            .banner {
                background: linear-gradient(to bottom, var(--header-gradient-start), var(--header-gradient-end));
                color: var(--header-text);
                text-align: center;
                padding: 30px 0;
            }

            .banner h1 {
                font-size: 2.5rem;
                letter-spacing: 2px;
            }

            /* LOGIN FORM STYLES */
            .login-section {
                flex: 1;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 40px 20px;
            }

            .login-container {
                width: 100%;
                max-width: 400px;
                padding: 30px;
                border: 1px solid var(--container-border);
                background-color: var(--card-bg);
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .login-header {
                text-align: center;
                margin-bottom: 20px;
            }

            .login-form {
                display: flex;
                flex-direction: column;
            }

            .input-group {
                margin-bottom: 20px;
            }

            .input-group label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
            }

            .input-group input {
                width: 100%;
                padding: 12px;
                border: 1px solid var(--input-border);
                border-radius: 4px;
                background-color: var(--card-bg);
                color: var(--text-color);
                font-size: 16px;
            }

            .input-group input:focus {
                outline: none;
                border-color: var(--header-gradient-start);
            }

            .remember-me {
                display: flex;
                align-items: center;
                margin-bottom: 20px;
            }

            .remember-me input {
                margin-right: 10px;
            }

            .login-button {
                background: var(--header-gradient-start);
                color: var(--header-text);
                border: none;
                padding: 12px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                font-weight: bold;
                transition: background 0.3s ease;
            }

            .login-button:hover {
                background: var(--button-hover);
            }

            .form-footer {
                margin-top: 20px;
                text-align: center;
            }

            .form-footer a {
                color: var(--header-gradient-start);
                text-decoration: none;
            }

            .form-footer a:hover {
                text-decoration: underline;
            }

            /* ERROR MESSAGE STYLES */
            .error-message {
                background-color: var(--error-color);
                color: white;
                padding: 10px;
                border-radius: 4px;
                margin-bottom: 20px;
                text-align: center;
            }

            .success-message {
                background-color: #2ecc71;
                color: white;
                padding: 10px;
                border-radius: 4px;
                margin-bottom: 20px;
                text-align: center;
            }

            /* FOOTER STYLES */
            .footer {
                background-color: var(--header-gradient-start);
                color: var(--header-text);
                padding: 15px;
                text-align: center;
            }

            /* RESPONSIVE STYLES */
            @media (max-width: 768px) {
                .banner h1 {
                    font-size: 2rem;
                }

                .login-container {
                    padding: 20px;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <!-- Header -->
            <header>
                <nav class="navbar">
                    <div class="nav-item logo">
                        <a href="home_page.html">LOGO</a>
                    </div>
                </nav>

                <div class="banner">
                    <h1>ĐĂNG NHẬP</h1>
                </div>
            </header>

            <!-- Login Section -->
            <section class="login-section">
                <div class="login-container">
                    <div class="login-header">
                        <h2>Đăng nhập vào tài khoản của bạn</h2>
                    </div>

                    <!-- Hiển thị thông báo lỗi nếu có -->
                    <% if(request.getAttribute("errorMessage") != null) { %>
                        <div class="error-message">
                            <%= request.getAttribute("errorMessage") %>
                        </div>
                    <% } %>
                    
                    <!-- Hiển thị thông báo thành công nếu có -->
                    <% if(request.getAttribute("successMessage") != null) { %>
                        <div class="success-message">
                            <%= request.getAttribute("successMessage") %>
                        </div>
                    <% } %>

                    <!-- Kiểm tra xem có cookie lưu email không -->
                    <%
                        String savedEmail = "";
                        Cookie[] cookies = request.getCookies();
                        if (cookies != null) {
                            for (Cookie cookie : cookies) {
                                if (cookie.getName().equals("savedEmail")) {
                                    savedEmail = cookie.getValue();
                                    break;
                                }
                            }
                        }
                    %>

                      <!-- Form đăng nhập - sẽ được xử lý bởi Servlet -->
                    <form class="login-form" action="login" method="post">
                        <div class="input-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" 
                                   placeholder="Nhập địa chỉ email" required
                                   value="<%= savedEmail %>">
                        </div>

                        <div class="input-group">
                            <label for="password">Mật khẩu</label>
                            <input type="password" id="password" name="password" 
                                   placeholder="Nhập mật khẩu" required>
                        </div>

                        <div class="remember-me">
                            <input type="checkbox" id="remember" name="remember"
                                   <%= !savedEmail.isEmpty() ? "checked" : "" %>>
                            <label for="remember">Ghi nhớ đăng nhập</label>
                        </div>

                        <button type="submit" class="login-button">Đăng nhập</button>
                    </form>

                    <div class="form-footer">
                        <p>Chưa có tài khoản? <a href="register.jsp">Đăng ký ngay</a></p>
                        <p><a href="forgot_password.jsp">Quên mật khẩu?</a></p>
                    </div>
                </div>
            </section>

            <!-- Footer -->
            <footer class="footer">
                <p>© 2025 Chia Sẻ Trang Web. Tất cả quyền được bảo lưu.</p>
            </footer>
        </div>
    </body>
</html>
