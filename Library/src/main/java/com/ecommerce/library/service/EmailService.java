package com.ecommerce.library.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface EmailService {
    void sendVerificationEmail(String toEmail, String token,String name) throws MessagingException, UnsupportedEncodingException;
}
