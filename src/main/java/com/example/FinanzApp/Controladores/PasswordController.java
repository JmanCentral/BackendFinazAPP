package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.Config.JwtUtils;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.example.FinanzApp.Servicios.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/password")
public class PasswordController {

    private final JwtUtils jwtUtil;
    private final EmailService emailService;
    private final RepositorioUsuario repositorioUsuario;

    public PasswordController(JwtUtils jwtUtil, EmailService emailService, RepositorioUsuario repositorioUsuario) {
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.repositorioUsuario = repositorioUsuario;
    }

    @PostMapping("/forgot")
    @ResponseBody
    public String forgotPassword(@RequestParam String email) {
        Optional<Usuario> usuario = repositorioUsuario.findByEmail(email);

        if (usuario.isEmpty()) {
            return "El correo no está registrado.";
        }

        String token = jwtUtil.generateTokenEmail(email);
        String resetLink = "http://localhost:8080/password/reset?token=" + token;
        emailService.sendPasswordResetEmail(email, resetLink);

        return "Correo de recuperación enviado.";
    }

    @GetMapping("/reset")
    public String showResetForm(@RequestParam("token") String token, Model model) {
        String email = jwtUtil.extractEmail(token);

        if (email == null || !jwtUtil.validateTokenEmail(token, email)) {
            model.addAttribute("error", "Token inválido o expirado.");
            return "reset-password"; // Redirige a la página con un mensaje de error
        }

        model.addAttribute("token", token);
        return "reset-password"; // Muestra el formulario para restablecer la contraseña
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword, Model model) {
        String email = jwtUtil.extractEmail(token);
        Optional<Usuario> usuario = repositorioUsuario.findByEmail(email);

        if (usuario.isEmpty() || !jwtUtil.validateTokenEmail(token, email)) {
            model.addAttribute("error", "Token inválido o expirado.");
            return "reset-password";
        }

        Usuario user = usuario.get();
        user.setContrasena(newPassword); // Encriptar en producción
        repositorioUsuario.save(user);

        model.addAttribute("success", "Contraseña actualizada correctamente.");
        return "reset-password";
    }
}
