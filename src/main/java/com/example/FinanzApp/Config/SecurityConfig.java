package com.example.FinanzApp.Config;


import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.example.FinanzApp.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
                    auth.requestMatchers("Finanzapp/registro", "/Finanzapp/login").permitAll();
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

