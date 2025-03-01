package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {
    
    public static void sendEmail(String to, String subject, String body) throws MessagingException {
        // Cấu hình thông tin email
        final String username = "your-email@gmail.com"; // Thay bằng email của bạn
        final String password = "your-app-password"; // Thay bằng mật khẩu ứng dụng
        
        // Thiết lập các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        // Tạo session với xác thực
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        // Tạo message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);
        
        // Gửi email
        Transport.send(message);
    }
}
