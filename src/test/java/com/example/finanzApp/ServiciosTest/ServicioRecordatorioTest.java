package com.example.finanzApp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.finanzApp.DTOS.RecordatorioDTO;
import com.example.finanzApp.Entidades.Recordatorio;
import com.example.finanzApp.Entidades.Usuario;
import com.example.finanzApp.Repositorios.RepositorioRecordatorio;
import com.example.finanzApp.Repositorios.RepositorioUsuario;
import com.example.finanzApp.Servicios.ServicioRecordatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class ServicioRecordatorioTest {

    @Mock
    private RepositorioRecordatorio repositorioRecordatorio;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServicioRecordatorio servicioRecordatorio;

    private RecordatorioDTO recordatorioDTO;
    private Recordatorio recordatorio;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        recordatorioDTO = new RecordatorioDTO();
        recordatorioDTO.setId_recordatorio(1L);
        recordatorioDTO.setNombre("Pago de servicios");
        recordatorioDTO.setEstado("Pendiente");
        recordatorioDTO.setFecha(LocalDate.parse("2023-10-15"));
        recordatorioDTO.setDias_recordatorio(3L);
        recordatorioDTO.setValor(150.0);

        recordatorio = new Recordatorio();
        recordatorio.setId_recordatorio(1L);
        recordatorio.setNombre("Pago de servicios");
        recordatorio.setEstado("Pendiente");
        recordatorio.setFecha(LocalDate.parse("2023-10-15"));
        recordatorio.setDias_recordatorio(3L);
        recordatorio.setValor(150.0);

        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");
    }

    @Test
    public void testRegistrarRecordatorio() {
        // Configuración de los mocks
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(modelMapper.map(recordatorioDTO, Recordatorio.class)).thenReturn(recordatorio);
        when(repositorioRecordatorio.save(recordatorio)).thenReturn(recordatorio);
        when(modelMapper.map(recordatorio, RecordatorioDTO.class)).thenReturn(recordatorioDTO);

        // Ejecución del método a probar
        RecordatorioDTO resultado = servicioRecordatorio.RegistrarRecordatorio(recordatorioDTO, 1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(recordatorioDTO.getNombre(), resultado.getNombre());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioRecordatorio, times(1)).save(recordatorio);
    }

    @Test
    public void testListarRecordatorios() {
        // Configuración de los mocks
        when(repositorioRecordatorio.findByUsuarioId(1L)).thenReturn(Arrays.asList(recordatorio));
        when(modelMapper.map(recordatorio, RecordatorioDTO.class)).thenReturn(recordatorioDTO);

        // Ejecución del método a probar
        List<RecordatorioDTO> resultados = servicioRecordatorio.ListarRecordatorios(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(recordatorioDTO.getNombre(), resultados.get(0).getNombre());
        verify(repositorioRecordatorio, times(1)).findByUsuarioId(1L);
    }

    @Test
    public void testModificarRecordatorio() {
        // Configuración de los mocks
        when(repositorioRecordatorio.findById(1L)).thenReturn(Optional.of(recordatorio));
        when(repositorioRecordatorio.save(recordatorio)).thenReturn(recordatorio);

        // Ejecución del método a probar
        RecordatorioDTO resultado = servicioRecordatorio.ModificarRecordatorio(1L, recordatorioDTO);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(recordatorioDTO.getNombre(), resultado.getNombre());
        verify(repositorioRecordatorio, times(1)).findById(1L);
        verify(repositorioRecordatorio, times(1)).save(recordatorio);
    }

    @Test
    public void testEliminarRecordatorio() {
        // Ejecución del método a probar
        servicioRecordatorio.EliminarRecordatorio(1L);

        // Verificaciones
        verify(repositorioRecordatorio, times(1)).deleteById(1L);
    }

    @Test
    @Transactional
    public void testEliminarTodosLosRecordatorios() {
        // Ejecución del método a probar
        servicioRecordatorio.eliminarTodosLosRecordatorios(1L);

        // Verificaciones
        verify(repositorioRecordatorio, times(1)).deleteByUsuario(1L);
    }

    @Test
    public void testBuscarPorNombre() {
        // Configuración de los mocks
        when(repositorioRecordatorio.findByNombre("Pago de servicios")).thenReturn(Arrays.asList(recordatorio));
        when(modelMapper.map(recordatorio, RecordatorioDTO.class)).thenReturn(recordatorioDTO);

        // Ejecución del método a probar
        List<RecordatorioDTO> resultados = servicioRecordatorio.BuscarPorNombre("Pago de servicios");

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(recordatorioDTO.getNombre(), resultados.get(0).getNombre());
        verify(repositorioRecordatorio, times(1)).findByNombre("Pago de servicios");
    }
}