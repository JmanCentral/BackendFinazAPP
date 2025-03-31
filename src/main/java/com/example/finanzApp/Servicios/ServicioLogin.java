package com.example.finanzApp.Servicios;

import com.example.finanzApp.Config.JwtUtils;
import com.example.finanzApp.DTOS.LoginRequest;
import com.example.finanzApp.Entidades.Usuario;
import com.example.finanzApp.Repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ServicioLogin {


    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RepositorioUsuario repositorioUsuario;


    public Map<String, Object> AutenticarUsuario(LoginRequest loginRequest) {

            // 1. Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getContrasena()));

            // 2. Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Obtener el usuario desde la base de datos
            Usuario usuario = repositorioUsuario.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            // 4. Generar el token JWT
            String jwt = jwtUtils.generateToken(usuario.getId_usuario(), usuario.getUsername());

            // 5. Construir la respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Autenticación exitosa");
            response.put("id", usuario.getId_usuario());
            response.put("nombre", usuario.getUsername());
            response.put("token", jwt);

            return response;

    }
}
