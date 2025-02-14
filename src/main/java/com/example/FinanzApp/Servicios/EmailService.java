package com.example.FinanzApp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        String resetUrl = "http://localhost:8862/password/reset?token=" + token;
        String message = "Haz clic en el siguiente enlace para restablecer tu contraseña: " + resetUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject("Recuperación de Contraseña");
        email.setText(message);
        email.setFrom("tu_correo@gmail.com");

        mailSender.send(email);
    }
}




