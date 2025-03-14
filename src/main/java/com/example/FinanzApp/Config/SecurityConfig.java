package com.example.FinanzApp.Config;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.example.FinanzApp.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    JwtUtils jwtUtils;

    @Lazy
    @Autowired
    ServicioUsuario userDetailsService;

    @Lazy
    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;

    @Lazy
    @Autowired
    RepositorioUsuario repositorioUsuario;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity , AuthenticationManager authenticationManager) throws Exception {

        JwtAutenticationFilter jwtAutenticationFilter = new JwtAutenticationFilter(jwtUtils , repositorioUsuario);
        jwtAutenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAutenticationFilter.setFilterProcessesUrl("/Finanzapp/login");


        return httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(auth-> {
                    auth.requestMatchers("/Finanzapp/registro", "/Finanzapp/login" , "/api/password/forgot" , "/api/password/reset",  "/v3/api-docs/**",      // Documentación OpenAPI (Swagger 3)
                            "/swagger-ui/**",       // Interfaz web de Swagger
                            "/swagger-ui.html",     // Página principal de Swagger UI
                            "/webjars/**", "/Finanzapp/ObtenerUsuario/{id_usuario}","/Finanzapp/Consejos/Registrar").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(jwtAutenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity , PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();

    }

}

