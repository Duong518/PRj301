<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin người dùng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
        .update-btn {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
        }
        .delete-btn {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
        }
        .home-btn {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-top: 15px;
        }
        .message {
            margin-top: 20px;
            padding: 10px;
            border-radius: 4px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
        }
        .button-group {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <%
    // Kiểm tra session để đảm bảo người dùng đã đăng nhập
    String userEmail = (String) session.getAttribute("email");
    if (userEmail == null) {
        // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
        response.sendRedirect("login.jsp");
        return;
    }
    %>
    
    <div class="container">
        <h1>Thông tin người dùng</h1>
        
        <% if(request.getAttribute("message") != null) { %>
            <div class="message <%= request.getAttribute("messageType") %>">
                <%= request.getAttribute("message") %>
            </div>
        <% } %>
        
        <form action="UserInfoServlet" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" id="email" name="email" value="<%= userEmail %>" readonly>
            </div>
            
            <div class="form-group">
                <label for="fullName">Họ và tên:</label>
                <input type="text" id="fullName" name="fullName" value="${userInfo.fullName}">
            </div>
            
            <div class="form-group">
                <label for="age">Tuổi:</label>
                <input type="number" id="age" name="age" value="${userInfo.age}">
            </div>
            
            <div class="form-group">
                <label for="address">Địa chỉ:</label>
                <input type="text" id="address" name="address" value="${userInfo.address}">
            </div>
            
            <div class="form-group">
                <label for="phone">Số điện thoại:</label>
                <input type="number" id="phone" name="phone" value="${userInfo.phone}">
            </div>
            
            <div class="buttons">
                <button type="button" class="delete-btn" onclick="confirmDelete()">Xóa tài khoản</button>
                <button type="submit" class="update-btn">Cập nhật</button>
            </div>
        </form>
        
        <div class="button-group">
            <a href="home_page_login.jsp" class="home-btn">Quay lại trang chủ</a>
        </div>
    </div>
    
    <script>
        function confirmDelete() {
            if (confirm("Bạn có chắc chắn muốn xóa tài khoản? Hành động này không thể hoàn tác.")) {
                window.location.href = "DeleteAccountServlet";
            }
        }
    </script>
</body>
</html>
