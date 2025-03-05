package com.example.FinanzApp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.FinanzApp.Components.CodigoGenerador;
import com.example.FinanzApp.DTOS.AlcanciaDTO;
import com.example.FinanzApp.Entidades.Alcancia;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioAlcancia;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.example.FinanzApp.Servicios.ServicioAlcancia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class ServicioAlcanciaTest {

    @Mock
    private RepositorioAlcancia repositorioAlcancia;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CodigoGenerador codigoGenerador;

    @InjectMocks
    private ServicioAlcancia servicioAlcancia;

    private AlcanciaDTO alcanciaDTO;
    private Alcancia alcancia;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        alcanciaDTO = new AlcanciaDTO();
        alcanciaDTO.setIdAlcancia(1L);
        alcanciaDTO.setNombre_alcancia("Alcancía de vacaciones");
        alcanciaDTO.setMeta(1000.0);
        alcanciaDTO.setSaldoActual(200.0);
        alcanciaDTO.setCodigo("ABC123");
        alcanciaDTO.setFechaCreacion(LocalDate.parse("2023-10-01"));

        alcancia = new Alcancia();
        alcancia.setIdAlcancia(1L);
        alcancia.setNombre_alcancia("Alcancía de vacaciones");
        alcancia.setMeta(1000.0);
        alcancia.setSaldoActual(200.0);
        alcancia.setCodigo("ABC123");
        alcancia.setFechaCreacion(LocalDate.parse("2023-10-01"));

        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");
    }

    @Test
    public void testCrearAlcancia() {
        // Configuración de los mocks
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(modelMapper.map(alcanciaDTO, Alcancia.class)).thenReturn(alcancia);
        when(codigoGenerador.generarCodigo()).thenReturn("ABC123");
        when(repositorioAlcancia.save(alcancia)).thenReturn(alcancia);
        when(modelMapper.map(alcancia, AlcanciaDTO.class)).thenReturn(alcanciaDTO);

        // Ejecución del método a probar
        AlcanciaDTO resultado = servicioAlcancia.crearAlcancia(alcanciaDTO, 1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(alcanciaDTO.getNombre_alcancia(), resultado.getNombre_alcancia());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioAlcancia, times(1)).save(alcancia);
    }

    @Test
    public void testBuscarAlcancia() {
        // Configuración de los mocks
        when(repositorioAlcancia.findByCodigo("ABC123")).thenReturn(Arrays.asList(alcancia));
        when(modelMapper.map(alcancia, AlcanciaDTO.class)).thenReturn(alcanciaDTO);

        // Ejecución del método a probar
        List<AlcanciaDTO> resultados = servicioAlcancia.buscarAlcancia("ABC123");

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(alcanciaDTO.getNombre_alcancia(), resultados.get(0).getNombre_alcancia());
        verify(repositorioAlcancia, times(1)).findByCodigo("ABC123");
    }

    @Test
    public void testBuscarAlcanciasPorUser() {
        // Configuración de los mocks
        when(repositorioAlcancia.findAlcanciasByUserId(1L)).thenReturn(Arrays.asList(alcancia));
        when(modelMapper.map(alcancia, AlcanciaDTO.class)).thenReturn(alcanciaDTO);

        // Ejecución del método a probar
        List<AlcanciaDTO> resultados = servicioAlcancia.buscarAlcanciasporUser(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(alcanciaDTO.getNombre_alcancia(), resultados.get(0).getNombre_alcancia());
        verify(repositorioAlcancia, times(1)).findAlcanciasByUserId(1L);
    }

    @Test
    public void testModificarAlcancia() {
        // Configuración de los mocks
        when(repositorioAlcancia.findById(1L)).thenReturn(Optional.of(alcancia));
        when(repositorioAlcancia.save(alcancia)).thenReturn(alcancia);

        // Ejecución del método a probar
        AlcanciaDTO resultado = servicioAlcancia.ModificarAlcancia(alcanciaDTO, 1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(alcanciaDTO.getNombre_alcancia(), resultado.getNombre_alcancia());
        verify(repositorioAlcancia, times(1)).findById(1L);
        verify(repositorioAlcancia, times(1)).save(alcancia);
    }

    @Test
    public void testEliminarAlcancia() {
        // Ejecución del método a probar
        servicioAlcancia.eliminarAlcancia(1L);

        // Verificaciones
        verify(repositorioAlcancia, times(1)).deleteById(1L);
    }
}
