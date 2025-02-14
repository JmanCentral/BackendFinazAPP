package com.example.FinanzApp.Controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@RestController
@RequestMapping("/password")
public class ControladorContraseña {

    @Autowired
     JwtUtil jwtUtil;

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/forgot")
    public String forgotPassword(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "El correo no está registrado.";
        }

        String token = jwtUtil.generateToken(email);
        emailService.sendPasswordResetEmail(email, token);

        return "Correo de recuperación enviado.";
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email);

        if (user == null || !jwtUtil.validateToken(token, email)) {
            return "Token inválido o expirado.";
        }

        user.setPassword(newPassword); // Encriptar antes en producción
        userRepository.save(user);

        return "Contraseña actualizada correctamente.";
    }

}
*/