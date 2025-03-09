package com.example.FinanzApp.Config;

import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@AllArgsConstructor
public class JwtAutenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final JwtUtils jwtUtils;
    private final RepositorioUsuario repositorioUsuario;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Usuario usuario = null;
        String username = "";
        String password = "";

        try{
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            username = usuario.getUsername();
            password = usuario.getContrasena();

        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken
                (username, password);

        return getAuthenticationManager().authenticate(authRequest);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal(); // Obtiene UserDetails

        // Obtener el usuario completo desde la base de datos
        Usuario usuario = repositorioUsuario.findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Generar token con el ID del usuario incluido
        String token = jwtUtils.generateToken(usuario.getId_usuario(), usuario.getUsername()); // 👈 Modifica jwtUtils

        response.addHeader("Authorization", "Bearer " + token);

        Map<String , Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("mensaje", "Autenticación exitosa");
        httpResponse.put("id", usuario.getId_usuario()); // 👈 Agregar ID
        httpResponse.put("nombre", usuario.getUsername());

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(200);
        response.setContentType("application/json");
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

}
