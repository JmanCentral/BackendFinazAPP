package com.example.FinanzApp.Config;

import com.example.FinanzApp.Entidades.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
@AllArgsConstructor

public class JwtAutenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Usuario usuario;
        String username;
        String contrasena;

        try {
            // Lee el cuerpo de la solicitud
            String requestBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            System.out.println("Cuerpo de la solicitud: " + requestBody);

            // Convierte el JSON en un objeto Usuario
            usuario = new ObjectMapper().readValue(requestBody, Usuario.class);
            username = usuario.getUsername();
            contrasena = usuario.getContrasena();

            System.out.println("Usuario deserializado: " + usuario);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Error al leer el cuerpo de la solicitud", e);
        }

        // Crea el token de autenticación
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, contrasena);

        // Autentica al usuario
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        chain.doFilter(request, response);
    }

}
