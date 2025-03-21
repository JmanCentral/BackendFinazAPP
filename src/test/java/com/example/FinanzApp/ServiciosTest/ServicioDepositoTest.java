package com.example.FinanzApp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.FinanzApp.DTOS.DepositoDTO;
import com.example.FinanzApp.Entidades.Alcancia;
import com.example.FinanzApp.Entidades.Deposito;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioAlcancia;
import com.example.FinanzApp.Repositorios.RepositorioDeposito;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import com.example.FinanzApp.Servicios.ServicioDeposito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class ServicioDepositoTest {

    @Mock
    private RepositorioDeposito repositorioDeposito;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private RepositorioAlcancia repositorioAlcancia;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServicioDeposito servicioDeposito;

    private DepositoDTO depositoDTO;
    private Deposito deposito;
    private Usuario usuario;
    private Alcancia alcancia;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        depositoDTO = new DepositoDTO();
        depositoDTO.setIdDeposito(1L);
        depositoDTO.setMonto(100.0);
        depositoDTO.setFecha(LocalDate.parse("2023-10-01"));
        depositoDTO.setNombre_depositante("Juan");

        deposito = new Deposito();
        deposito.setIdDeposito(1L);
        deposito.setMonto(100.0);
        deposito.setFecha(LocalDate.parse("2023-10-01"));
        deposito.setNombre_depositante("Juan");

        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");

        alcancia = new Alcancia();
        alcancia.setIdAlcancia(1L);
        alcancia.setNombre_alcancia("Alcancía de vacaciones");
        alcancia.setMeta(1000.0);
        alcancia.setSaldoActual(200.0);
    }

    @Test
    public void testRealizarDeposito() {
        // Configuración de los mocks
        when(repositorioUsuario.findById(1L)).thenReturn(Optional.of(usuario));
        when(repositorioAlcancia.findById(1L)).thenReturn(Optional.of(alcancia));
        when(modelMapper.map(depositoDTO, Deposito.class)).thenReturn(deposito);
        when(repositorioAlcancia.save(alcancia)).thenReturn(alcancia);
        when(repositorioDeposito.save(deposito)).thenReturn(deposito);
        when(modelMapper.map(deposito, DepositoDTO.class)).thenReturn(depositoDTO);

        // Ejecución del método a probar
        DepositoDTO resultado = servicioDeposito.realizarDeposito(depositoDTO, 1L, 1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(depositoDTO.getMonto(), resultado.getMonto());
        verify(repositorioUsuario, times(1)).findById(1L);
        verify(repositorioAlcancia, times(1)).findById(1L);
        verify(repositorioDeposito, times(1)).save(deposito);
    }

    @Test
    public void testObtenerDepositos() {
        // Configuración de los mocks
        when(repositorioDeposito.findByAlcancia(1L)).thenReturn(Arrays.asList(deposito));
        when(modelMapper.map(deposito, DepositoDTO.class)).thenReturn(depositoDTO);

        // Ejecución del método a probar
        List<DepositoDTO> resultados = servicioDeposito.ObtenerDepositos(1L);

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(depositoDTO.getMonto(), resultados.get(0).getMonto());
        verify(repositorioDeposito, times(1)).findByAlcancia(1L);
    }

    @Test
    public void testObtenerValorGastosMesDepositos() {
        // Configuración de los mocks
        when(repositorioDeposito.getValorDepositosMes(1L)).thenReturn(500.0);

        // Ejecución del método a probar
        Double resultado = servicioDeposito.ObtenerValorGastosMesDepositos(1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(500.0, resultado);
        verify(repositorioDeposito, times(1)).getValorDepositosMes(1L);
    }

    @Test
    @Transactional
    public void testModificarDeposito() {
        // Configuración de los mocks
        deposito.setAlcancia(alcancia);  // Asegurar que el depósito tiene una alcancía asociada
        when(repositorioDeposito.findById(1L)).thenReturn(Optional.of(deposito));
        when(repositorioAlcancia.save(alcancia)).thenReturn(alcancia);
        when(repositorioDeposito.save(deposito)).thenReturn(deposito);

        // Ejecución del método a probar
        DepositoDTO resultado = servicioDeposito.ModificarDeposito(depositoDTO, 1L, 1L);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(depositoDTO.getMonto(), resultado.getMonto());
        verify(repositorioDeposito, times(1)).findById(1L);
        verify(repositorioDeposito, times(1)).save(deposito);
    }


    @Test
    @Transactional
    public void testEliminarDeposito() {
        // Configuración de los mocks
        when(repositorioDeposito.findById(1L)).thenReturn(Optional.of(deposito));
        when(repositorioAlcancia.findById(1L)).thenReturn(Optional.of(alcancia));
        doNothing().when(repositorioDeposito).deleteByDepositos(1L, 1L);

        // Ejecución del método a probar
        servicioDeposito.EliminarDeposito(1L, 1L);

        // Verificaciones
        verify(repositorioDeposito, times(1)).deleteByDepositos(1L, 1L);
        verify(repositorioAlcancia, times(1)).save(alcancia);
    }
}