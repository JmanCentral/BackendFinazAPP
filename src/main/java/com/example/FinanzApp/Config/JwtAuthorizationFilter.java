package com.example.FinanzApp.Config;

import com.example.FinanzApp.Servicios.ServicioUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
   private JwtUtils jwtUtils;

    @Autowired
    private ServicioUsuario userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

   String tokenHeader  = request.getHeader("Authorization");


   if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
       String token = tokenHeader.substring(7);

       if (jwtUtils.validateToken(token)){
           String username = jwtUtils.getUsername(token);
           UserDetails userDetails = userDetailsService.loadUserByUsername(username);

           System.out.println("Roles del usuario '" + username + "':");

           userDetails.getAuthorities().forEach(auth -> System.out.println(auth.getAuthority()));

           UsernamePasswordAuthenticationToken authentication =
                   new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

           SecurityContextHolder.getContext().setAuthentication(authentication);
       }


   }
        filterChain.doFilter(request, response);

    }
}
