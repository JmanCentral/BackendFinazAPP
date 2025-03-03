package com.example.FinanzApp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.Entidades.Ingreso;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioIngreso;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.example.FinanzApp.Servicios.ServicioIngreso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class ServicioIngresoTest {

    @Mock
    private RepositorioIngreso repositorioIngreso;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServicioIngreso servicioIngreso;

    private IngresoDTO ingresoDTO;
    private Ingreso ingreso;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        ingresoDTO = new IngresoDTO();
        ingresoDTO.setId_ingreso(1L);
        ingresoDTO.setNombre_ingreso("Salario");
        ingresoDTO.setTipo_ingreso("Mensual");
        ingresoDTO.setValor(1000.0);
        ingresoDTO.setFecha(LocalDate.parse("2023-10-01"));

        ingreso = new Ingreso();
        ingreso.setId_ingreso(1L);
        ingreso.setNombre_ingreso("Salario");
        ingreso.setTipo_ingreso("Mensual");
        ingreso.setValor(1000.0);
        ingreso.setFecha(LocalDate.parse("2023-10-01"));

        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");
    }

    @Test
    public void testRegistrarIngreso() {
        // Configuración de los mocks
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(modelMapper.map(ingresoDTO, Ingreso.class)).thenReturn(ingreso);
        when(repositorioIngreso.save(ingreso)).thenReturn(ingreso);
        when(modelMapper.map(ingreso, IngresoDTO.class)).thenReturn(ingresoDTO);

        // Ejecución del método a probar
        IngresoDTO resultado = servicioIngreso.RegistrarIngreso(ingresoDTO, 1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(ingresoDTO.getNombre_ingreso(), resultado.getNombre_ingreso());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioIngreso, times(1)).save(ingreso);
    }

    @Test
    public void testBuscarIngresosCasualesPorAnio() {
        // Configuración de los mocks
        when(repositorioIngreso.verificacion(1L)).thenReturn(Arrays.asList(ingreso));
        when(modelMapper.map(ingreso, IngresoDTO.class)).thenReturn(ingresoDTO);

        // Ejecución del método a probar
        List<IngresoDTO> resultados = servicioIngreso.BuscarIngresosCasualesPorAnio(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(ingresoDTO.getNombre_ingreso(), resultados.get(0).getNombre_ingreso());
        verify(repositorioIngreso, times(1)).verificacion(1L);
    }

    @Test
    public void testBuscarIngresosMensuales() {
        // Configuración de los mocks
        when(repositorioIngreso.findIngresosMensualesByUsuarioId(1L)).thenReturn(Arrays.asList(ingreso));
        when(modelMapper.map(ingreso, IngresoDTO.class)).thenReturn(ingresoDTO);

        // Ejecución del método a probar
        List<IngresoDTO> resultados = servicioIngreso.BuscarIngresosMensuales(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(ingresoDTO.getNombre_ingreso(), resultados.get(0).getNombre_ingreso());
        verify(repositorioIngreso, times(1)).findIngresosMensualesByUsuarioId(1L);
    }

    @Test
    public void testBuscarIngresosCasuales() {
        // Configuración de los mocks
        when(repositorioIngreso.findIngresosCasualesDelMes(1L)).thenReturn(Arrays.asList(ingreso));
        when(modelMapper.map(ingreso, IngresoDTO.class)).thenReturn(ingresoDTO);

        // Ejecución del método a probar
        List<IngresoDTO> resultados = servicioIngreso.BuscarIngresosCasuales(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(ingresoDTO.getNombre_ingreso(), resultados.get(0).getNombre_ingreso());
        verify(repositorioIngreso, times(1)).findIngresosCasualesDelMes(1L);
    }

    @Test
    public void testBuscarIngresosTotales() {
        // Configuración de los mocks
        when(repositorioIngreso.getIngTotalDeEsteMes(1L)).thenReturn(1000.0);

        // Ejecución del método a probar
        Double resultado = servicioIngreso.BuscarIngresosTotales(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(1000.0, resultado);
        verify(repositorioIngreso, times(1)).getIngTotalDeEsteMes(1L);
    }

    @Test
    public void testBuscarIngresosMensualesConAnioYMes() {
        // Configuración de los mocks
        when(repositorioIngreso.getIngresosMensuales(1L, 2023, 10)).thenReturn(Arrays.asList(ingreso));
        when(modelMapper.map(ingreso, IngresoDTO.class)).thenReturn(ingresoDTO);

        // Ejecución del método a probar
        List<IngresoDTO> resultados = servicioIngreso.BuscarIngresosMensuales(1L, 2023, 10);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(ingresoDTO.getNombre_ingreso(), resultados.get(0).getNombre_ingreso());
        verify(repositorioIngreso, times(1)).getIngresosMensuales(1L, 2023, 10);
    }

    @Test
    public void testModificarIngreso() {
        // Configuración de los mocks
        when(repositorioIngreso.findById(1L)).thenReturn(Optional.of(ingreso));
        when(repositorioIngreso.save(ingreso)).thenReturn(ingreso);

        // Ejecución del método a probar
        IngresoDTO resultado = servicioIngreso.ModificarIngreso(1L, ingresoDTO);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(ingresoDTO.getNombre_ingreso(), resultado.getNombre_ingreso());
        verify(repositorioIngreso, times(1)).findById(1L);
        verify(repositorioIngreso, times(1)).save(ingreso);
    }

    @Test
    public void testProyectarIngresos() {
        // Configuración de los mocks
        when(repositorioIngreso.calcularTotalMensual(1L)).thenReturn(2000.0);

        // Ejecución del método a probar
        Double resultado = servicioIngreso.ProyectarIngresos(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(2000.0, resultado);
        verify(repositorioIngreso, times(1)).calcularTotalMensual(1L);
    }

    @Test
    public void testAhorroMensual() {
        // Configuración de los mocks
        when(repositorioIngreso.calcularAhorroPosible(1L)).thenReturn(500.0);

        // Ejecución del método a probar
        Double resultado = servicioIngreso.AhorroMensual(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(500.0, resultado);
        verify(repositorioIngreso, times(1)).calcularAhorroPosible(1L);
    }

    @Test
    public void testEliminarIngreso() {

        servicioIngreso.eliminarIngreso(1L);

        verify(repositorioIngreso, times(1)).deleteById(1L);
    }
}
