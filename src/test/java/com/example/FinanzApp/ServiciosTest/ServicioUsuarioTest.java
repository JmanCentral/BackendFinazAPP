package com.example.FinanzApp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.FinanzApp.DTOS.UsuarioDTO;
import com.example.FinanzApp.Entidades.ERole;
import com.example.FinanzApp.Entidades.Roles;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioRoles;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.example.FinanzApp.Servicios.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class ServicioUsuarioTest {

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
        usuarioDTO.setRoles(Set.of("ROLE_USER"));

        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");
        usuario.setEmail("test@example.com");
        usuario.setContrasena("encodedPassword");

        rol = new Roles();
        rol.setId(1L);
        rol.setName(ERole.USER);
    }

    @Test
    public void testRegistrarUsuario() {
        when(modelMapper.map(usuarioDTO, Usuario.class)).thenReturn(usuario);
        when(passwordEncoder.encode(usuarioDTO.getContrasena())).thenReturn("encodedPassword");
        when(repositorioRoles.findByName(ERole.USER)).thenReturn(Optional.of(rol));
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

        UsuarioDTO result = servicioUsuario.obtenerUusarioPorID(1L);

        assertNotNull(result);
        assertEquals(usuarioDTO.getUsername(), result.getUsername());
    }

    @Test
    public void testObtenerUsuarioPorID_NotFound() {
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.empty());

        UsuarioDTO result = servicioUsuario.obtenerUusarioPorID(1L);

        assertNull(result);
    }

}