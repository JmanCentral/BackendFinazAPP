package com.example.FinanzApp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.FinanzApp.DTOS.AlertaDTO;
import com.example.FinanzApp.Entidades.Alerta;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioAlerta;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.example.FinanzApp.Servicios.ServicioAlerta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class ServicioAlertaTest {

    @Mock
    private RepositorioAlerta repositorioAlerta;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServicioAlerta servicioAlerta;

    private AlertaDTO alertaDTO;
    private Alerta alerta;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        alertaDTO = new AlertaDTO();
        alertaDTO.setId_alerta(1L);
        alertaDTO.setNombre("Alerta de gasto");
        alertaDTO.setDescripcion("Gasto excesivo en comida");
        alertaDTO.setValor(100.0);
        alertaDTO.setFecha(LocalDate.parse("2023-10-01"));

        alerta = new Alerta();
        alerta.setId_alerta(1L);
        alerta.setNombre("Alerta de gasto");
        alerta.setDescripcion("Gasto excesivo en comida");
        alerta.setValor(100.0);
        alerta.setFecha(LocalDate.parse("2023-10-01"));

        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");
    }

    @Test
    public void testRegistrarAlerta() {
        // Configuración de los mocks
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(modelMapper.map(alertaDTO, Alerta.class)).thenReturn(alerta);
        when(repositorioAlerta.save(alerta)).thenReturn(alerta);
        when(modelMapper.map(alerta, AlertaDTO.class)).thenReturn(alertaDTO);

        // Ejecución del método a probar
        AlertaDTO resultado = servicioAlerta.RegistrarAlerta(alertaDTO, 1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(alertaDTO.getNombre(), resultado.getNombre());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioAlerta, times(1)).save(alerta);
    }

    @Test
    public void testObtenerAlerta() {
        // Configuración de los mocks
        when(repositorioAlerta.getAlertasPorUsuario(1L)).thenReturn(Arrays.asList(alerta));
        when(modelMapper.map(alerta, AlertaDTO.class)).thenReturn(alertaDTO);

        // Ejecución del método a probar
        List<AlertaDTO> resultados = servicioAlerta.ObtenerAlerta(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(alertaDTO.getNombre(), resultados.get(0).getNombre());
        verify(repositorioAlerta, times(1)).getAlertasPorUsuario(1L);
    }

    @Test
    public void testObtenerAlertaFecha() {
        // Configuración de los mocks
        when(repositorioAlerta.getAlertasDeEsteAno(1L)).thenReturn(Arrays.asList(alerta));
        when(modelMapper.map(alerta, AlertaDTO.class)).thenReturn(alertaDTO);

        // Ejecución del método a probar
        List<AlertaDTO> resultados = servicioAlerta.ObtenerAlertaFecha(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(alertaDTO.getNombre(), resultados.get(0).getNombre());
        verify(repositorioAlerta, times(1)).getAlertasDeEsteAno(1L);
    }

    @Test
    public void testObtenerAlertaEsteMes() {
        // Configuración de los mocks
        when(repositorioAlerta.getAlertasDeEsteMes(1L)).thenReturn(Arrays.asList(alerta));
        when(modelMapper.map(alerta, AlertaDTO.class)).thenReturn(alertaDTO);

        // Ejecución del método a probar
        List<AlertaDTO> resultados = servicioAlerta.ObtenerAlertaEsteMes(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(alertaDTO.getNombre(), resultados.get(0).getNombre());
        verify(repositorioAlerta, times(1)).getAlertasDeEsteMes(1L);
    }

    @Test
    public void testModificarAlerta() {
        // Configuración de los mocks
        when(repositorioAlerta.findById(1L)).thenReturn(Optional.of(alerta));
        when(repositorioAlerta.save(alerta)).thenReturn(alerta);

        // Ejecución del método a probar
        AlertaDTO resultado = servicioAlerta.ModificarAlerta(1L, alertaDTO);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(alertaDTO.getNombre(), resultado.getNombre());
        verify(repositorioAlerta, times(1)).findById(1L);
        verify(repositorioAlerta, times(1)).save(alerta);
    }

    @Test
    public void testEliminarAlerta() {
        // Ejecución del método a probar
        servicioAlerta.EliminarAlerta(1L);

        // Verificaciones
        verify(repositorioAlerta, times(1)).deleteById(1L);
    }
}
