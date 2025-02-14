/*
package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.Servicios.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class ControladorEmail {

    @Autowired
    EmailService emailService;

    @GetMapping("/send")
    public String sendEmail() {
        emailService.sendEmail("destinatario@correo.com", "Asunto de prueba", "Este es un correo de prueba.");
        return "Correo enviado exitosamente.";
    }
}

 */
