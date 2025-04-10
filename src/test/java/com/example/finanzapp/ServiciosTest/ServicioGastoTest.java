package com.example.finanzapp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.finanzapp.DTOS.CategoriaTotalDTO;
import com.example.finanzapp.DTOS.GastoDTO;
import com.example.finanzapp.DTOS.ProyeccionDTO;
import com.example.finanzapp.Entidades.CategoriaTotal;
import com.example.finanzapp.Entidades.Gasto;
import com.example.finanzapp.Entidades.GastoProjection;
import com.example.finanzapp.Entidades.Usuario;
import com.example.finanzapp.Repositorios.RepositorioGasto;
import com.example.finanzapp.Repositorios.RepositorioIngreso;
import com.example.finanzapp.Repositorios.RepositorioUsuario;
import com.example.finanzapp.Servicios.ServicioGasto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class ServicioGastoTest {

    @Mock
    private RepositorioGasto repositorioGasto;

    @Mock
    private RepositorioIngreso repositorioIngreso;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServicioGasto servicioGasto;

    private GastoDTO gastoDTO;
    private Gasto gasto;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        gastoDTO = new GastoDTO();
        gastoDTO.setId_gasto(1L);
        gastoDTO.setNombre_gasto("Comida");
        gastoDTO.setCategoria("Alimentación");
        gastoDTO.setFecha(LocalDate.of(2023, 10, 1));
        gastoDTO.setValor(50.0);

        gasto = new Gasto();
        gasto.setId_gasto(1L);
        gasto.setNombre_gasto("Comida");
        gasto.setCategoria("Alimentación");
        gasto.setFecha(LocalDate.of(2023, 10, 1));
        gasto.setValor(50.0);

        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");
    }

    @Test
    public void testRegistrarGasto() {
        // Configuración de los mocks
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(modelMapper.map(gastoDTO, Gasto.class)).thenReturn(gasto);
        when(repositorioGasto.save(gasto)).thenReturn(gasto);
        when(modelMapper.map(gasto, GastoDTO.class)).thenReturn(gastoDTO);

        // Ejecución del método a probar
        GastoDTO resultado = servicioGasto.RegistrarGasto(gastoDTO, 1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(gastoDTO.getNombre_gasto(), resultado.getNombre_gasto());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioGasto, times(1)).save(gasto);
    }

    @Test
    public void testObtenerDisponible() {
        // Configuración de los mocks
        when(repositorioGasto.getDisponible(1L)).thenReturn(1000.0);

        // Ejecución del método a probar
        Double resultado = servicioGasto.ObtenerDisponible(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(1000.0, resultado);
        verify(repositorioGasto, times(1)).getDisponible(1L);
    }

    @Test
    public void testObtenerDisponiblePorFechas() {
        // Configuración de los mocks
        LocalDate fechaInicio = LocalDate.of(2023, 10, 1);
        LocalDate fechaFin = LocalDate.of(2023, 10, 31);
        when(repositorioGasto.getDisponiblePorFechas(1L, fechaInicio, fechaFin)).thenReturn(500.0);

        // Ejecución del método a probar
        Double resultado = servicioGasto.ObtenerDisponiblePorFechas(1L, fechaInicio, fechaFin);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(500.0, resultado);
        verify(repositorioGasto, times(1)).getDisponiblePorFechas(1L, fechaInicio, fechaFin);
    }

    @Test
    public void testBuscarGastosMesCategoria() {
        // Configuración de los mocks
        when(repositorioGasto.getGastosMesCategoria(1L, "Alimentación")).thenReturn(Arrays.asList(gasto));
        when(modelMapper.map(gasto, GastoDTO.class)).thenReturn(gastoDTO);

        // Ejecución del método a probar
        List<GastoDTO> resultados = servicioGasto.BuscarGastosMesCategoria(1L, "Alimentación");

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(gastoDTO.getNombre_gasto(), resultados.get(0).getNombre_gasto());
        verify(repositorioGasto, times(1)).getGastosMesCategoria(1L, "Alimentación");
    }

    @Test
    public void testObtenerValorGastosMesCategoria() {
        // Configuración de los mocks
        when(repositorioGasto.getValorGastosMesCategoria(1L, "Alimentación")).thenReturn(200.0);

        // Ejecución del método a probar
        Double resultado = servicioGasto.ObtenerValorGastosMesCategoria(1L, "Alimentación");

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(200.0, resultado);
        verify(repositorioGasto, times(1)).getValorGastosMesCategoria(1L, "Alimentación");
    }

    @Test
    public void testValorGastosMes() {
        // Configuración de los mocks
        when(repositorioGasto.getValorGastosMes(1L)).thenReturn(1000.0);

        // Ejecución del método a probar
        Double resultado = servicioGasto.ValorGastosMes(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(1000.0, resultado);
        verify(repositorioGasto, times(1)).getValorGastosMes(1L);
    }

    @Test
    public void testBuscarGastosPorFechas() {
        // Configuración de los mocks
        LocalDate fechaInicio = LocalDate.of(2023, 10, 1);
        LocalDate fechaFin = LocalDate.of(2023, 10, 31);
        when(repositorioGasto.getGastosPorFechas(1L, fechaInicio, fechaFin)).thenReturn(Arrays.asList(gasto));
        when(modelMapper.map(gasto, GastoDTO.class)).thenReturn(gastoDTO);

        // Ejecución del método a probar
        List<GastoDTO> resultados = servicioGasto.BuscarGastosPorFechas(1L, fechaInicio, fechaFin);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(gastoDTO.getNombre_gasto(), resultados.get(0).getNombre_gasto());
        verify(repositorioGasto, times(1)).getGastosPorFechas(1L, fechaInicio, fechaFin);
    }

    @Test
    public void testOrdenarAscendentemente() {
        // Configuración de los mocks
        when(repositorioGasto.findByUsuarioIdOrderByValorAsc(1L)).thenReturn(Arrays.asList(gasto));
        when(modelMapper.map(gasto, GastoDTO.class)).thenReturn(gastoDTO);

        // Ejecución del método a probar
        List<GastoDTO> resultados = servicioGasto.OrdenarAscendentemente(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(gastoDTO.getNombre_gasto(), resultados.get(0).getNombre_gasto());
        verify(repositorioGasto, times(1)).findByUsuarioIdOrderByValorAsc(1L);
    }

    @Test
    public void testOrdenarDescendentemente() {
        // Configuración de los mocks
        when(repositorioGasto.findByUsuarioIdOrderByValorDesc(1L)).thenReturn(Arrays.asList(gasto));
        when(modelMapper.map(gasto, GastoDTO.class)).thenReturn(gastoDTO);

        // Ejecución del método a probar
        List<GastoDTO> resultados = servicioGasto.OrdenarDescendentemente(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(gastoDTO.getNombre_gasto(), resultados.get(0).getNombre_gasto());
        verify(repositorioGasto, times(1)).findByUsuarioIdOrderByValorDesc(1L);
    }

    @Test
    public void testObtenerGastosPorRangoDeFechas() {
        // Configuración de los mocks
        LocalDate fechaInicio = LocalDate.of(2023, 10, 1);
        LocalDate fechaFin = LocalDate.of(2023, 10, 31);
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(repositorioGasto.findByUsuarioAndFechaBetweenAndCategoria(usuario, fechaInicio, fechaFin, "Alimentación")).thenReturn(Arrays.asList(gasto));
        when(modelMapper.map(gasto, GastoDTO.class)).thenReturn(gastoDTO);

        // Ejecución del método a probar
        List<GastoDTO> resultados = servicioGasto.obtenerGastosPorRangoDeFechas(1L, fechaInicio, fechaFin, "Alimentación");

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(gastoDTO.getNombre_gasto(), resultados.get(0).getNombre_gasto());
        verify(repositorioGasto, times(1)).findByUsuarioAndFechaBetweenAndCategoria(usuario, fechaInicio, fechaFin, "Alimentación");
    }

    @Test
    public void testOrdenarPorValorAlto() {
        // Configuración de los mocks
        when(repositorioGasto.getValorMasAlto(1L)).thenReturn(gasto);
        when(modelMapper.map(gasto, GastoDTO.class)).thenReturn(gastoDTO);

        // Ejecución del método a probar
        GastoDTO resultado = servicioGasto.OrdenarPorValorAlto(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(gastoDTO.getNombre_gasto(), resultado.getNombre_gasto());
        verify(repositorioGasto, times(1)).getValorMasAlto(1L);
    }

    @Test
    public void testOrdenarPorValorBajo() {
        // Configuración de los mocks
        when(repositorioGasto.getValorMasBajo(1L)).thenReturn(gasto);
        when(modelMapper.map(gasto, GastoDTO.class)).thenReturn(gastoDTO);

        // Ejecución del método a probar
        GastoDTO resultado = servicioGasto.OrdenarPorValorBajo(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(gastoDTO.getNombre_gasto(), resultado.getNombre_gasto());
        verify(repositorioGasto, times(1)).getValorMasBajo(1L);
    }

    @Test
    public void testObtenerPromedioDeGastos() {
        // Configuración de los mocks
        when(repositorioGasto.getPromedioGastosMes(1L)).thenReturn(500.0);

        // Ejecución del método a probar
        Double resultado = servicioGasto.ObtenerPromedioDeGastos(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(500.0, resultado);
        verify(repositorioGasto, times(1)).getPromedioGastosMes(1L);
    }

    @Test
    public void testObtenerGastoRecurrente() {
        // Configuración de los mocks
        when(repositorioGasto.getDescripcionRecurrente(1L)).thenReturn("Comida");

        // Ejecución del método a probar
        String resultado = servicioGasto.ObtenerGastoRecurrente(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("Comida", resultado);
        verify(repositorioGasto, times(1)).getDescripcionRecurrente(1L);
    }

    @Test
    public void testPorcentajeGastosSobreIngresos() {
        // Configuración de los mocks
        when(repositorioGasto.getPorcentajeGastosSobreIngresos(1L)).thenReturn(75.0);

        // Ejecución del método a probar
        Double resultado = servicioGasto.PorcentajeGastosSobreIngresos(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(75.0, resultado);
        verify(repositorioGasto, times(1)).getPorcentajeGastosSobreIngresos(1L);
    }

    @Test
    public void testObtenerPromedioDiario() {
        // Configuración de los mocks
        when(repositorioGasto.getGastoPromedioDiarioTotal(1L)).thenReturn(50.0);

        // Ejecución del método a probar
        Double resultado = servicioGasto.ObtenerPromedioDiario(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(50.0, resultado);
        verify(repositorioGasto, times(1)).getGastoPromedioDiarioTotal(1L);
    }

    @Test
    public void testModificarGasto() {
        // Configuración de los mocks
        when(repositorioGasto.findById(1L)).thenReturn(Optional.of(gasto));
        when(repositorioGasto.save(gasto)).thenReturn(gasto);

        // Ejecución del método a probar
        GastoDTO resultado = servicioGasto.ModificarGasto(1L, gastoDTO);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(gastoDTO.getNombre_gasto(), resultado.getNombre_gasto());
        verify(repositorioGasto, times(1)).findById(1L);
        verify(repositorioGasto, times(1)).save(gasto);
    }

    @Test
    public void testEliminarGasto() {
        // Ejecución del método a probar
        servicioGasto.EliminarGasto(1L);

        // Verificaciones
        verify(repositorioGasto, times(1)).deleteById(1L);
    }

    @Test
    public void testListarPorNombres() {
        // Configuración de los mocks
        when(repositorioGasto.findByNombreGastoAndCategoriaAndUsuarioId("Comida", "Alimentación", 1L)).thenReturn(Arrays.asList(gasto));
        when(modelMapper.map(gasto, GastoDTO.class)).thenReturn(gastoDTO);

        // Ejecución del método a probar
        List<GastoDTO> resultados = servicioGasto.ListarPorNombres("Comida", "Alimentación", 1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(gastoDTO.getNombre_gasto(), resultados.get(0).getNombre_gasto());
        verify(repositorioGasto, times(1)).findByNombreGastoAndCategoriaAndUsuarioId("Comida", "Alimentación", 1L);
    }

    @Test
    @Transactional
    public void testEliminarTodosLosGastos() {
        // Ejecución del método a probar
        servicioGasto.eliminarTodosLosGastos("Alimentación", 1L);

        // Verificaciones
        verify(repositorioGasto, times(1)).deleteByUsuarioIdAndCategoria(1L, "Alimentación");
    }

    @Test
    public void testObtenerGastosFrecuentes() {
        // Configuración de los mocks
        GastoProjection gastoProjection = mock(GastoProjection.class);
        when(gastoProjection.getDescripcion()).thenReturn("Comida");
        when(gastoProjection.getCantidad()).thenReturn(5);
        when(gastoProjection.getTotal()).thenReturn(250.0);
        when(repositorioGasto.findGastosFrecuentes(1L)).thenReturn(Arrays.asList(gastoProjection));

        // Ejecución del método a probar
        List<ProyeccionDTO> resultados = servicioGasto.obtenerGastosFrecuentes(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Comida", resultados.get(0).getDescripcion());
        assertEquals(5, resultados.get(0).getCantidad());
        assertEquals(250.0, resultados.get(0).getTotal());
        verify(repositorioGasto, times(1)).findGastosFrecuentes(1L);
    }

    @Test
    public void testObtenerCategoriaMasAlta() {
        // Configuración de los mocks
        CategoriaTotal categoriaTotal = new CategoriaTotal() {
            @Override
            public String getCategoria() {
                return "Alimentación";
            }

            @Override
            public Double getTotalvalor() {
                return 1000.0;
            }
        };
        when(repositorioGasto.getCategoriaConMasGastos(1L)).thenReturn(categoriaTotal);

        // Ejecución del método a probar
        CategoriaTotalDTO resultado = servicioGasto.obtenerCategoriaMasAlta(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("Alimentación", resultado.getCategoria());
        assertEquals(1000.0, resultado.getTotalvalor());
        verify(repositorioGasto, times(1)).getCategoriaConMasGastos(1L);
    }

    @Test
    public void testGetCategoriaConMasGastos() {
        // Configuración de los mocks
        List<Gasto> gastos = Arrays.asList(
                new Gasto(1L, "Comida", "Alimentación", LocalDate.now(), 100.0, usuario),
                new Gasto(2L, "Transporte", "Transporte", LocalDate.now(), 50.0, usuario)
        );
        when(repositorioGasto.findByUsuario(1L)).thenReturn(gastos);

        // Ejecución del método a probar
        CategoriaTotalDTO resultado = servicioGasto.getCategoriaConMasGastos(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("Alimentación", resultado.getCategoria());
        assertEquals(100.0, resultado.getTotalvalor());
        verify(repositorioGasto, times(1)).findByUsuario(1L);
    }



}
