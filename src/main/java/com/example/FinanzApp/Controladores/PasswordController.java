package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.Config.JwtUtils;
import com.example.FinanzApp.DTOS.EmailRequest;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.example.FinanzApp.Servicios.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/password")
public class PasswordController {

    @Autowired
    JwtUtils jwtUtil;
    @Autowired
    EmailService emailService;
    @Autowired
    RepositorioUsuario repositorioUsuario;
    @Autowired
    PasswordEncoder passwordEncoder;


    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailRequest emailRequest) {
        Optional<Usuario> usuario = repositorioUsuario.findByEmail(emailRequest.getEmail());

        if (usuario.isEmpty()) {
            return ResponseEntity.badRequest().body("El correo no está registrado.");
        }

        String token = jwtUtil.generateTokenEmail(emailRequest.getEmail());
        emailService.sendPasswordResetEmail(emailRequest.getEmail(), token); // Solo pasamos el token

        return ResponseEntity.ok("Correo de recuperación enviado.");
    }



    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        String email = jwtUtil.extractEmail(token);
        Optional<Usuario> usuario = repositorioUsuario.findByEmail(email);

        if (usuario.isEmpty() || !jwtUtil.validateTokenEmail(token, email)) {
            return ResponseEntity.badRequest().body("Token inválido o expirado.");
        }

        Usuario user = usuario.get();
        user.setContrasena(passwordEncoder.encode(newPassword)); // Asegurar encriptación en producción
        repositorioUsuario.save(user);

        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}
