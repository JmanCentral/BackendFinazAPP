package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.Config.JwtUtils;
import com.example.FinanzApp.DTOS.LoginRequest;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
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

    //Inyección de dependencias
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RepositorioUsuario repositorioUsuario;

    /**
     * Autentica a un usuario usando su nombre de usuario y contraseña.
     *
     * Proceso:
     * 1. Verifica las credenciales del usuario con {@link AuthenticationManager}.
     * 2. Establece el contexto de seguridad de Spring con la autenticación.
     * 3. Recupera los detalles del usuario desde la base de datos.
     * 4. Genera un token JWT para el usuario autenticado.
     * 5. Construye y devuelve una respuesta con los datos del usuario y el token.
     *
     * @param loginRequest Objeto que contiene el nombre de usuario y la contraseña.
     * @return Mapa con mensaje, ID del usuario, nombre de usuario y token JWT.
     * @throws UsernameNotFoundException Si el usuario no es encontrado en la base de datos.
     * @throws //BadCredentialsException Si las credenciales proporcionadas no son válidas.
     */
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
