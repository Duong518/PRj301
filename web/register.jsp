<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký tài khoản</title>
    <style>
        /* CSS cho form đăng ký */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        
        .register-container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 100%;
            max-width: 400px;
        }
        
        .register-header {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .register-header h2 {
            color: #333;
            margin: 0;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }
        
        .form-group input:focus {
            border-color: #4CAF50;
            outline: none;
        }
        
        .recovery-code {
            background-color: #f8f9fa;
            border: 1px dashed #ccc;
            padding: 10px;
            text-align: center;
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 20px;
            letter-spacing: 2px;
        }
        
        .recovery-note {
            color: #dc3545;
            font-size: 14px;
            margin-top: 5px;
            text-align: center;
        }
        
        .error-message {
            color: #f44336;
            font-size: 14px;
            margin-top: 5px;
        }
        
        .submit-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 12px;
            width: 100%;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
        }
        
        .submit-button:hover {
            background-color: #45a049;
        }
        
        .login-link {
            text-align: center;
            margin-top: 20px;
        }
        
        .login-link a {
            color: #4CAF50;
            text-decoration: none;
        }
        
        .login-link a:hover {
            text-decoration: underline;
        }
        
        .print-button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin-top: 5px;
        }
        
        .print-button:hover {
            background-color: #0069d9;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <div class="register-header">
            <h2>Đăng ký tài khoản</h2>
        </div>
        
        <!-- Hiển thị thông báo lỗi nếu có -->
        <% if(request.getAttribute("errorMessage") != null) { %>
            <div class="error-message" style="text-align: center; margin-bottom: 15px;">
                <%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>
        
        <form action="register" method="post" id="registerForm">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required 
                       value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">
                <div class="error-message" id="emailError"></div>
            </div>
            
            <div class="form-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required>
                <div class="error-message" id="passwordError"></div>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Xác nhận mật khẩu:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <div class="error-message" id="confirmPasswordError"></div>
            </div>
            
            <div class="form-group">
                <label>Mã khôi phục của bạn:</label>
                <div class="recovery-code" id="recoveryCode">
                    <!-- Mã khôi phục sẽ được tạo bằng JavaScript -->
                </div>
                <input type="hidden" id="recoveryCodeInput" name="recoveryCode">
                <div class="recovery-note">
                    <strong>Quan trọng:</strong> Hãy lưu lại mã khôi phục này. Bạn sẽ cần nó để khôi phục tài khoản nếu quên mật khẩu.
                
            <button type="submit" class="submit-button">Đăng ký</button>
        </form>
        
        <div class="login-link">
            Đã có tài khoản? <a href="login.jsp">Đăng nhập</a>
        </div>
    </div>
    
    <script>
        // Tạo mã khôi phục ngẫu nhiên khi trang được tải
        document.addEventListener('DOMContentLoaded', function() {
            generateRecoveryCode();
        });
        
        // Hàm tạo mã khôi phục ngẫu nhiên
        function generateRecoveryCode() {
            const length = 8;
            let result = '';
            // Sử dụng cả chữ và số để tạo mã khôi phục an toàn hơn
            const characters = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
            for (let i = 0; i < length; i++) {
                result += characters.charAt(Math.floor(Math.random() * characters.length));
            }
            
            document.getElementById('recoveryCode').textContent = result;
            document.getElementById('recoveryCodeInput').value = result;
        }
        
        // Hàm in mã khôi phục
        function printRecoveryCode() {
            const recoveryCode = document.getElementById('recoveryCode').textContent;
            const email = document.getElementById('email').value || 'your email';
            
            const printWindow = window.open('', '', 'height=400,width=600');
            printWindow.document.write('<html><head><title>Mã khôi phục tài khoản</title>');
            printWindow.document.write('<style>body { font-family: Arial, sans-serif; padding: 20px; } .container { border: 1px solid #ccc; padding: 20px; max-width: 500px; margin: 0 auto; } h2 { text-align: center; } .code { font-size: 24px; text-align: center; letter-spacing: 2px; margin: 20px 0; padding: 10px; background-color: #f8f9fa; border: 1px dashed #ccc; } .note { color: #dc3545; }</style>');
            printWindow.document.write('</head><body>');
            printWindow.document.write('<div class="container">');
            printWindow.document.write('<h2>Mã khôi phục tài khoản</h2>');
            printWindow.document.write('<p>Email: ' + email + '</p>');
            printWindow.document.write('<div class="code">' + recoveryCode + '</div>');
            printWindow.document.write('<p class="note">Quan trọng: Hãy lưu giữ mã khôi phục này ở nơi an toàn. Bạn sẽ cần nó để khôi phục tài khoản nếu quên mật khẩu.</p>');
            printWindow.document.write('</div>');
            printWindow.document.write('</body></html>');
            printWindow.document.close();
            printWindow.focus();
            
            setTimeout(function() {
                printWindow.print();
                printWindow.close();
            }, 500);
        }
        
        // JavaScript cho việc xác thực form phía client
        document.getElementById('registerForm').addEventListener('submit', function(event) {
            let hasError = false;
            
            // Reset error messages
            document.getElementById('emailError').textContent = '';
            document.getElementById('passwordError').textContent = '';
            document.getElementById('confirmPasswordError').textContent = '';
            
            // Validate email
            const email = document.getElementById('email').value.trim();
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                document.getElementById('emailError').textContent = 'Email không hợp lệ';
                hasError = true;
            }
            
            // Validate password
            const password = document.getElementById('password').value;
            if (password.length < 6) {
                document.getElementById('passwordError').textContent = 'Mật khẩu phải có ít nhất 6 ký tự';
                hasError = true;
            }
            
            // Validate confirm password
            const confirmPassword = document.getElementById('confirmPassword').value;
            if (password !== confirmPassword) {
                document.getElementById('confirmPasswordError').textContent = 'Mật khẩu xác nhận không khớp';
                hasError = true;
            }
            
            if (hasError) {
                event.preventDefault(); // Prevent form submission if there are errors
            }
        });
    </script>
</body>
</html>
