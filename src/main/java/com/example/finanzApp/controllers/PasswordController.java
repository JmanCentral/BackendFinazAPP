package com.example.finanzApp.controllers;

import com.example.finanzApp.Config.JwtUtils;
import com.example.finanzApp.DTOS.EmailRequest;
import com.example.finanzApp.DTOS.RespuestaCorreo;
import com.example.finanzApp.Entidades.Usuario;
import com.example.finanzApp.Repositorios.RepositorioUsuario;
import com.example.finanzApp.Servicios.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/password")
public class PasswordController {


    private final JwtUtils jwtUtil;

    private final EmailService emailService;

    private final RepositorioUsuario repositorioUsuario;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/forgot")
    public ResponseEntity<RespuestaCorreo> forgotPassword(@RequestBody EmailRequest emailRequest) {
        Optional<Usuario> usuario = repositorioUsuario.findByEmail(emailRequest.getEmail());

        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {

            String token = jwtUtil.generateTokenEmail(emailRequest.getEmail());
            emailService.sendPasswordResetEmail(emailRequest.getEmail(), token); // Solo pasamos el token

            return ResponseEntity.ok(new RespuestaCorreo("Correo de recuperación enviado.", true));

        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword) {

        String email = jwtUtil.extractEmail(token);
        Optional<Usuario> usuario = repositorioUsuario.findByEmail(email);

        if (usuario.isEmpty() || !jwtUtil.validateTokenEmail(token, email)) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\":\"Token inválido o expirado.\"}");
        }

        Usuario user = usuario.get();
        user.setContrasena(passwordEncoder.encode(newPassword));
        repositorioUsuario.save(user);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\":\"Contraseña actualizada correctamente.\",\"username\":\"" + user.getUsername() + "\"}");
    }


}
