package com.example.FinanzApp.Servicios;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private static final String FRONTEND_URL = "http://192.168.10.10:4200/reset-password?token=";

    @Async
    public void sendPasswordResetEmail(String to, String token) {
        String resetUrl = FRONTEND_URL + token; // Construcción de la URL aquí
        String message = "Haz clic en el siguiente enlace para restablecer tu contraseña: " + resetUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject("Recuperación de Contraseña");
        email.setText(message);
        email.setFrom("tu_correo@gmail.com");

        mailSender.send(email);
    }
}





