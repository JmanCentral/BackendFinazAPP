package com.example.finanzapp.Servicios;

import com.example.finanzapp.Config.JwtUtils;
import com.example.finanzapp.DTOS.LoginRequest;
import com.example.finanzapp.Entidades.Intentos;
import com.example.finanzapp.Entidades.Usuario;
import com.example.finanzapp.Excepciones.Usuario.CredencialesIncorrectasException;
import com.example.finanzapp.Excepciones.Usuario.UsuarioNoEncontradoException;
import com.example.finanzapp.Repositorios.RepositorioIntentos;
import com.example.finanzapp.Repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ServicioLogin {

    //Inyección de dependencias
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioIntentos repositorioIntentos;

    public Map<String, Object> AutenticarUsuario(LoginRequest loginRequest) {
        // 1. Obtener el usuario desde la base de datos
        Usuario usuario = repositorioUsuario.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsuarioNoEncontradoException("El username proporcionado no existe"));

        // 2. Obtener o crear registro de intentos
        Intentos intentos = repositorioIntentos.findByUsuario(usuario)
                .orElseGet(() -> {
                    Intentos nuevo = new Intentos();
                    nuevo.setUsuario(usuario);
                    nuevo.setIntentosFallidos(0);
                    nuevo.setHoraBloqueo(null);
                    return nuevo;
                });

        // 3. Verificar si el usuario está bloqueado
        if (intentos.getHoraBloqueo() != null && intentos.getHoraBloqueo().isAfter(LocalDateTime.now())) {
            Duration tiempoRestante = Duration.between(LocalDateTime.now(), intentos.getHoraBloqueo());
            throw new RuntimeException("Cuenta bloqueada. Intenta nuevamente en " + tiempoRestante.toMinutes() + " minutos.");
        }

        // 4. Intentar autenticar
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getContrasena()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Login exitoso, reiniciar intentos
            intentos.setIntentosFallidos(0);
            intentos.setHoraBloqueo(null);
            repositorioIntentos.save(intentos);

        } catch (BadCredentialsException e) {
            int intentosFallidos = intentos.getIntentosFallidos() + 1;
            intentos.setIntentosFallidos(intentosFallidos);

            if (intentosFallidos >= 5) {
                intentos.setHoraBloqueo(LocalDateTime.now().plusMinutes(5));
            }

            repositorioIntentos.save(intentos);
            throw new CredencialesIncorrectasException("Credenciales incorrectas. Intentos restantes: " + (5 - intentosFallidos));
        }

        // 5. Generar el token JWT
        String jwt = jwtUtils.generateToken(usuario.getId_usuario(), usuario.getUsername());

        // 6. Construir la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Autenticación exitosa");
        response.put("id", usuario.getId_usuario());
        response.put("nombre", usuario.getUsername());
        response.put("token", jwt);

        return response;
    }
}
