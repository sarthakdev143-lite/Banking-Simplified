package github.sarthakdev143.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendOtpEmail(String to, String otp) throws MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Your OTP Code");

            // Create Thymeleaf context and process the OTP email template
            Context context = new Context();
            context.setVariable("otp", otp);
            String body = templateEngine.process("email-template", context);

            // Set email body as HTML
            helper.setText(body, true);

            // Send the email
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Printing Exception : " + e);
        }
    }
}