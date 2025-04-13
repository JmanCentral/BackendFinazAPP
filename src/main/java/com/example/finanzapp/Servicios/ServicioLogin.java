package com.example.finanzapp.Servicios;

import com.example.finanzapp.Config.JwtUtils;
import com.example.finanzapp.DTOS.LoginRequest;
import com.example.finanzapp.Entidades.Usuario;
import com.example.finanzapp.Excepciones.Usuario.CredencialesIncorrectasException;
import com.example.finanzapp.Excepciones.Usuario.UsuarioNoEncontradoException;
import com.example.finanzapp.Repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    //Inyección de dependencias
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RepositorioUsuario repositorioUsuario;

    public Map<String, Object> AutenticarUsuario(LoginRequest loginRequest) {

        // 1. Obtener el usuario desde la base de datos
        Usuario usuario = repositorioUsuario.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsuarioNoEncontradoException("El username proporcionado no existe"));

        // 2. Autenticar al usuario
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getContrasena()));

            // 3. Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new CredencialesIncorrectasException("Credenciales incorrectas : intentalo de nuevo");
        }

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
