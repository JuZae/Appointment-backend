package de.example.APoint.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailOne(String to, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true indicates HTML content is present
        } catch (MessagingException e) {
            // Handle the exception appropriately
            throw new RuntimeException("Failed to send email", e);
        }

        javaMailSender.send(mimeMessage);
    }

    public void sendEmailMultiple(String[] to, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true indicates HTML content is present
        } catch (MessagingException e) {
            // Handle the exception appropriately
            throw new RuntimeException("Failed to send email", e);
        }

        javaMailSender.send(mimeMessage);
    }
}
