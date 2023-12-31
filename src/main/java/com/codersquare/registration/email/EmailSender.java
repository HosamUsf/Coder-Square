package com.codersquare.registration.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSender {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailSender.class);

    private final JavaMailSender mailSender ;

    @Async
    public void sendEmail(String toEmail,String body){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("hosamyoussef305@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject("Confirm your email");

            mailSender.send(message);

            LOGGER.info("mail sent successfully to: " , toEmail);
        }catch (Exception e){
            LOGGER.error("failed to send email" ,e );
            throw new IllegalStateException("failed to send email");
        }


    }

}
