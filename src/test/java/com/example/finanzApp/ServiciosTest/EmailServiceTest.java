package com.example.finanzApp.ServiciosTest;

import static org.mockito.Mockito.*;

import com.example.finanzApp.Servicios.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendPasswordResetEmail() {
        // Datos de prueba
        String to = "usuario@example.com";
        String token = "abc123";

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Ejecución del método a probar
        emailService.sendPasswordResetEmail(to, token);

        // Verificaciones
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class)); // Verifica que se llamó a send
    }
}
