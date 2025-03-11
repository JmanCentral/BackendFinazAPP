package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.Config.JwtUtils;
import com.example.FinanzApp.DTOS.LoginRequest;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Login", description = "Encargado de la autenticación y autorización")
@RequestMapping("/Finanzapp")

public class ControladorLogin {

    private static final Logger logger = LoggerFactory.getLogger(ControladorLogin.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        logger.info("Iniciando autenticación para el usuario: {}", loginRequest.getUsername());

        try {
            // 1. Autenticar al usuario
            logger.debug("Autenticando al usuario: {}", loginRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getContrasena()));
            logger.info("Autenticación exitosa para el usuario: {}", loginRequest.getUsername());

            // 2. Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Autenticación establecida en el contexto de seguridad");

            // 3. Obtener el usuario desde la base de datos
            logger.debug("Buscando usuario en la base de datos: {}", loginRequest.getUsername());
            Usuario usuario = repositorioUsuario.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> {
                        logger.error("Usuario no encontrado: {}", loginRequest.getUsername());
                        return new UsernameNotFoundException("Usuario no encontrado");
                    });
            logger.info("Usuario encontrado: {}", usuario.getUsername());

            // 4. Generar el token JWT
            logger.debug("Generando token JWT para el usuario: {}", usuario.getUsername());
            String jwt = jwtUtils.generateToken(usuario.getId_usuario(), usuario.getUsername());
            logger.info("Token JWT generado exitosamente");

            // 5. Construir la respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Autenticación exitosa");
            response.put("id", usuario.getId_usuario());
            response.put("nombre", usuario.getUsername());
            response.put("token", jwt);

            logger.debug("Respuesta construida: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error durante la autenticación", e);

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Error durante la autenticación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
