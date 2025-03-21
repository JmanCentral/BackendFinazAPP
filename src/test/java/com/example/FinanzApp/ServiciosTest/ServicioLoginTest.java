package com.example.FinanzApp.ServiciosTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.FinanzApp.Config.JwtUtils;
import com.example.FinanzApp.DTOS.LoginRequest;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.example.FinanzApp.Servicios.ServicioLogin;

@ExtendWith(MockitoExtension.class)
class ServicioLoginTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @InjectMocks
    private ServicioLogin servicioLogin;

    private LoginRequest loginRequest;
    private Usuario usuario;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setContrasena("password");

        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");

        authentication = mock(Authentication.class);
    }

    @Test
    void testAutenticarUsuario_Exitoso() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(repositorioUsuario.findByUsername("testuser"))
                .thenReturn(Optional.of(usuario));
        when(jwtUtils.generateToken(usuario.getId_usuario(), usuario.getUsername()))
                .thenReturn("fake-jwt-token");

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        Map<String, Object> response = servicioLogin.AutenticarUsuario(loginRequest);

        assertNotNull(response);
        assertEquals("Autenticación exitosa", response.get("mensaje"));
        assertEquals(1L, response.get("id"));
        assertEquals("testuser", response.get("nombre"));
        assertEquals("fake-jwt-token", response.get("token"));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repositorioUsuario, times(1)).findByUsername("testuser");
        verify(jwtUtils, times(1)).generateToken(usuario.getId_usuario(), usuario.getUsername());
    }

    @Test
    void testAutenticarUsuario_UsuarioNoEncontrado() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(repositorioUsuario.findByUsername("testuser"))
                .thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> servicioLogin.AutenticarUsuario(loginRequest));

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testAutenticarUsuario_ErrorAutenticacion() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Error de autenticación"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> servicioLogin.AutenticarUsuario(loginRequest));

        assertFalse(exception.getMessage().contains("Error durante la autenticación"));
    }
}
