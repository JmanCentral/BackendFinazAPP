package com.example.FinanzApp.Config;

import com.example.FinanzApp.Entidades.Usuario;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAutenticationFilter extends UsernamePasswordAuthenticationFilter {


    private JwtUtils jwtUtils;

    public JwtAutenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;

    }

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

        User user = (User) authResult.getPrincipal();
        String token = jwtUtils.generateToken(user.getUsername());

        response.addHeader("Authorization", "Bearer " + token);

        Map<String , Object> httpPesponse = new HashMap<>();
        httpPesponse.put("token", token);
        httpPesponse.put("mensaje", "Autenticación exitosa");
        httpPesponse.put("nombre", user.getUsername());


        response.getWriter().write(new ObjectMapper().writeValueAsString(httpPesponse));
        response.setStatus(200);
        response.setContentType("application/json");
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
