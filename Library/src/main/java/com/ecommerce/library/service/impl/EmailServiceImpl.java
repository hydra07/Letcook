package com.ecommerce.library.service.impl;

import com.ecommerce.library.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void sendVerificationEmail(String toEmail, String token, String name) throws MessagingException, UnsupportedEncodingException {

        String from = "letcook.swp@gmail.com";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>" +
                "Please click the link below to verify your registration:<br>" +
                "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" +
                "Thank you,<br>" +
                "LetCook.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(from, "LetCook");
        helper.setTo(toEmail);
        helper.setSubject(subject);

        content = content.replace("[[name]]",name);
        String verifyURL = "http://localhost:8020/shop/verify/" + token;
        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        javaMailSender.send(message);

    }
}
