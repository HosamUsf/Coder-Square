package com.codersquare.registration.email;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSender {
    private  static final Logger LOGGER = LoggerFactory
            .getLogger(EmailSender.class);

    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(String toEmail, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper
                    = new MimeMessageHelper(message, "utf-8");


            helper.setFrom("hosamyoussef305@gmail.com");
            helper.setTo(toEmail);
            helper.setText(body, true);
            helper.setSubject("Confirm your email");

            mailSender.send(message);

            LOGGER.info("mail sent successfully to: {}", toEmail);
        } catch (Exception e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }


    }

}
