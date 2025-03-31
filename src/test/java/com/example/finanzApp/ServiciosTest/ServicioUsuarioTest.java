package com.example.finanzApp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.finanzApp.DTOS.UsuarioDTO;
import com.example.finanzApp.Entidades.ERole;
import com.example.finanzApp.Entidades.Roles;
import com.example.finanzApp.Entidades.Usuario;
import com.example.finanzApp.Repositorios.RepositorioRoles;
import com.example.finanzApp.Repositorios.RepositorioUsuario;
import com.example.finanzApp.Servicios.ServicioUsuario;

@ExtendWith(MockitoExtension.class)
class ServicioUsuarioTest {

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private RepositorioRoles repositorioRoles;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServicioUsuario servicioUsuario;

    private UsuarioDTO usuarioDTO;
    private Usuario usuario;
    private Roles rol;

    @BeforeEach
    public void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername("testuser");
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setContrasena("password");
        usuarioDTO.setRoles(Set.of("USER"));

        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");
        usuario.setEmail("test@example.com");
        usuario.setContrasena("encodedPassword");

        rol = new Roles();
        rol.setId(1L);
        rol.setName(ERole.USER);

        usuario.setRoles(new HashSet<>(Set.of(rol)));
    }

    @Test
    public void testRegistrarUsuario() {
        when(modelMapper.map(usuarioDTO, Usuario.class)).thenReturn(usuario);
        when(passwordEncoder.encode(usuarioDTO.getContrasena())).thenReturn("encodedPassword");
        when(repositorioRoles.findByName(ERole.USER)).thenReturn(Optional.of(rol)); // **Corregido**
        when(repositorioUsuario.save(usuario)).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);

        UsuarioDTO result = servicioUsuario.registrarUsuario(usuarioDTO);

        assertNotNull(result);
        assertEquals(usuarioDTO.getUsername(), result.getUsername());
        verify(repositorioUsuario, times(1)).save(usuario);
    }

    @Test
    public void testObtenerUsuarioPorID() {
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(modelMapper.map(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);

        UsuarioDTO result = servicioUsuario.obtenerUusarioPorID(1L); // **Corrección en el nombre del método**

        assertNotNull(result);
        assertEquals(usuarioDTO.getUsername(), result.getUsername());
    }

    @Test
    public void testObtenerUsuarioPorID_NotFound() {
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.empty());

        UsuarioDTO result = servicioUsuario.obtenerUusarioPorID(1L); // **Corrección en el nombre del método**

        assertNull(result);
    }

    @Test
    void testLoadUserByUsername_UsuarioExiste() {
        when(repositorioUsuario.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = servicioUsuario.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());

        Set<String> rolesEsperados = usuario.getRoles().stream()
                .map(role -> "ROLE_" + role.getName().name())
                .collect(Collectors.toSet());

        Set<String> rolesObtenidos = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertEquals(rolesEsperados, rolesObtenidos);

        verify(repositorioUsuario).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsername_UsuarioNoExiste() {
        when(repositorioUsuario.findByUsername("invalido")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            servicioUsuario.loadUserByUsername("invalido");
        });

        verify(repositorioUsuario).findByUsername("invalido");
    }
}
