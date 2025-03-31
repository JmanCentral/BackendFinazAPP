package com.example.finanzApp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.finanzApp.DTOS.CalificacionDTO;
import com.example.finanzApp.Entidades.Consejos;
import com.example.finanzApp.Entidades.Calificacion;
import com.example.finanzApp.Entidades.Usuario;
import com.example.finanzApp.Repositorios.RepositorioConsejos;
import com.example.finanzApp.Repositorios.RepositorioCalificacion;
import com.example.finanzApp.Repositorios.RepositorioUsuario;
import com.example.finanzApp.Servicios.ServicioCalificacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static graphql.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicioCalificacionTest {

    @Mock
    private RepositorioCalificacion repositorioCalificacion;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private RepositorioConsejos repositorioConsejos;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServicioCalificacion servicioCalificacion;

    private CalificacionDTO calificacionDTO;
    private Calificacion calificacion;
    private Usuario usuario;
    private Consejos consejos;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        calificacionDTO = new CalificacionDTO();
        calificacionDTO.setIdCalificacion(1L);
        calificacionDTO.setMe_gusta(1);
        calificacionDTO.setNo_me_gusta(1);
        calificacionDTO.setId_usuario(1L);
        calificacionDTO.setIdConsejo(1L);

        calificacion = new Calificacion();
        calificacion.setIdCalificacion(1L);
        calificacion.setMe_gusta(1);
        calificacion.setNo_me_gusta(1);


        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");

        consejos =  new Consejos();
        consejos.setIdConsejo(1L);
        consejos.setConsejo("Mejora tus arriendos");

    }

    @Test
    public void testRegistrarCalificacionExitoso() {
        // Configuración de los mocks
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(repositorioConsejos.findById(1L)).thenReturn(Optional.of(consejos));
        when(repositorioCalificacion.countLikesByUsuarioAndConsejo(1L, 1L)).thenReturn(0);
        when(repositorioCalificacion.countDislikesByUsuarioAndConsejo(1L, 1L)).thenReturn(0);
        when(modelMapper.map(calificacionDTO, Calificacion.class)).thenReturn(calificacion);
        when(repositorioCalificacion.save(any(Calificacion.class))).thenReturn(calificacion);
        when(modelMapper.map(calificacion, CalificacionDTO.class)).thenReturn(calificacionDTO);

        // Ejecución del método a probar
        CalificacionDTO resultado = servicioCalificacion.registrarCalificacion(calificacionDTO);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCalificacion());
        assertEquals(1, resultado.getMe_gusta());
        assertEquals(1, resultado.getNo_me_gusta());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioConsejos, times(1)).findById(1L);
        verify(repositorioCalificacion, times(1)).countLikesByUsuarioAndConsejo(1L, 1L);
        verify(repositorioCalificacion, times(1)).countDislikesByUsuarioAndConsejo(1L, 1L);
        verify(repositorioCalificacion, times(1)).save(any(Calificacion.class));
    }

    @Test
    public void testRegistrarCalificacionUsuarioNoEncontrado() {
        // Configuración de los mocks
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.empty());

        // Ejecución y verificación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioCalificacion.registrarCalificacion(calificacionDTO);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioConsejos, never()).findById(anyLong());
        verify(repositorioCalificacion, never()).save(any(Calificacion.class));
    }

    @Test
    public void testRegistrarCalificacionConsejoNoEncontrado() {
        // Configuración de los mocks
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(repositorioConsejos.findById(1L)).thenReturn(Optional.empty());

        // Ejecución y verificación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioCalificacion.registrarCalificacion(calificacionDTO);
        });

        assertEquals("Consejo no encontrado", exception.getMessage());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioConsejos, times(1)).findById(1L);
        verify(repositorioCalificacion, never()).save(any(Calificacion.class));
    }

    @Test
    public void testRegistrarCalificacionYaDioMeGusta() {
        // Configuración de los mocks
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(repositorioConsejos.findById(1L)).thenReturn(Optional.of(consejos));
        when(repositorioCalificacion.countLikesByUsuarioAndConsejo(1L, 1L)).thenReturn(1);

        // Ejecución y verificación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioCalificacion.registrarCalificacion(calificacionDTO);
        });

        assertEquals("Ya has dado 'Me gusta' a este consejo.", exception.getMessage());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioConsejos, times(1)).findById(1L);
        verify(repositorioCalificacion, times(1)).countLikesByUsuarioAndConsejo(1L, 1L);
        verify(repositorioCalificacion, never()).save(any(Calificacion.class));
    }

    @Test
    public void testRegistrarCalificacionYaDioNoMeGusta() {
        // Configuración de los mocks
        calificacionDTO.setMe_gusta(0);
        calificacionDTO.setNo_me_gusta(1);

        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(repositorioConsejos.findById(1L)).thenReturn(Optional.of(consejos));
        when(repositorioCalificacion.countDislikesByUsuarioAndConsejo(1L, 1L)).thenReturn(1);

        // Ejecución y verificación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioCalificacion.registrarCalificacion(calificacionDTO);
        });

        assertEquals("Ya has dado 'No me gusta' a este consejo.", exception.getMessage());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioConsejos, times(1)).findById(1L);
        verify(repositorioCalificacion, times(1)).countDislikesByUsuarioAndConsejo(1L, 1L);
        verify(repositorioCalificacion, never()).save(any(Calificacion.class));
    }

    @Test
    public void testListarCalificaciones() {
        // Configuración de los mocks
        Calificacion calificacion2 = new Calificacion();
        calificacion2.setIdCalificacion(2L);
        calificacion2.setMe_gusta(1);
        calificacion2.setNo_me_gusta(1);
        calificacion2.setUsuario(usuario);
        calificacion2.setConsejos(consejos);


        calificacion.setUsuario(usuario);
        calificacion.setConsejos(consejos);

        when(repositorioCalificacion.findAll()).thenReturn(Arrays.asList(calificacion, calificacion2));

        // Ejecución del método a probar
        List<CalificacionDTO> resultado = servicioCalificacion.listarCalificaciones();

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        // Verificar la primera calificación
        assertEquals(1L, resultado.get(0).getIdCalificacion());
        assertEquals(1, resultado.get(0).getMe_gusta());
        assertEquals(1, resultado.get(0).getNo_me_gusta());
        assertEquals(1L, resultado.get(0).getId_usuario()); // Verificar el ID del usuario
        assertEquals(1L, resultado.get(0).getIdConsejo()); // Verificar el ID del consejo

        // Verificar la segunda calificación
        assertEquals(2L, resultado.get(1).getIdCalificacion());
        assertEquals(1, resultado.get(1).getMe_gusta());
        assertEquals(1, resultado.get(1).getNo_me_gusta());
        assertEquals(1L, resultado.get(1).getId_usuario()); // Verificar el ID del usuario
        assertEquals(1L, resultado.get(1).getIdConsejo()); // Verificar el ID del consejo

        verify(repositorioCalificacion, times(1)).findAll();
    }


}
