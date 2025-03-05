package com.example.FinanzApp.ControladoresTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.FinanzApp.Controladores.ControladorIngreso;
import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.Servicios.ServicioIngreso;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebMvcTest(ControladorIngreso.class)
public class ControladorIngresoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioIngreso servicioIngreso;

    @Autowired
    private ObjectMapper objectMapper;

    private IngresoDTO ingresoDTO;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        ingresoDTO = new IngresoDTO();
        ingresoDTO.setId_ingreso(1L);
        ingresoDTO.setNombre_ingreso("Salario");
        ingresoDTO.setTipo_ingreso("Mensual");
        ingresoDTO.setValor(1000.0);
        ingresoDTO.setFecha(LocalDate.parse("2023-10-01"));
    }

    @Test
    public void testRegistrarIngreso() throws Exception {
        // Configuración del mock
        when(servicioIngreso.RegistrarIngreso(any(IngresoDTO.class), eq(1L))).thenReturn(ingresoDTO);

        // Ejecución de la solicitud HTTP
        mockMvc.perform(post("/Finanzapp/Ingresos/registrarIngreso/{id_usuario}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingresoDTO)))
                .andExpect(status().isOk()) // Verifica que el código de estado es 200
                .andExpect(jsonPath("$.nombre_ingreso").value("Salario")); // Verifica el contenido de la respuesta

        // Verifica que se llamó al servicio
        verify(servicioIngreso, times(1)).RegistrarIngreso(any(IngresoDTO.class), eq(1L));
    }

    @Test
    public void testRegistrarIngreso_Error() throws Exception {
        // Configuración del mock
        when(servicioIngreso.RegistrarIngreso(any(IngresoDTO.class), eq(1L))).thenReturn(null);

        // Ejecución de la solicitud HTTP
        mockMvc.perform(post("/Finanzapp/Ingresos/registrarIngreso/{id_usuario}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingresoDTO)))
                .andExpect(status().isBadRequest()); // Verifica que el código de estado es 400

        // Verifica que se llamó al servicio
        verify(servicioIngreso, times(1)).RegistrarIngreso(any(IngresoDTO.class), eq(1L));
    }

    @Test
    public void testListarIngresosCasualesPorAnio() throws Exception {

        List<IngresoDTO> ingresos = Arrays.asList(ingresoDTO);
        when(servicioIngreso.BuscarIngresosCasualesPorAnio(1L)).thenReturn(ingresos);

        // Ejecución de la solicitud HTTP
        mockMvc.perform(get("/Finanzapp/Ingresos/IngresosCasualesAnio/{id_usuario}", 1L))
                .andExpect(status().isOk()) // Verifica que el código de estado es 200
                .andExpect(jsonPath("$[0].nombre_ingreso").value("Salario")); // Verifica el contenido de la respuesta

        // Verifica que se llamó al servicio
        verify(servicioIngreso, times(1)).BuscarIngresosCasualesPorAnio(1L);
    }

    @Test
    public void testListarIngresosCasualesPorAnio_Vacio() throws Exception {
        // Configuración del mock
        when(servicioIngreso.BuscarIngresosCasualesPorAnio(1L)).thenReturn(Collections.emptyList());

        // Ejecución de la solicitud HTTP
        mockMvc.perform(get("/Finanzapp/Ingresos/IngresosCasualesAnio/{id_usuario}", 1L))
                .andExpect(status().isNoContent()); // Verifica que el código de estado es 204

        // Verifica que se llamó al servicio
        verify(servicioIngreso, times(1)).BuscarIngresosCasualesPorAnio(1L);
    }

    @Test
    public void testObtenerTotalIngresos() throws Exception {
        // Configuración del mock
        when(servicioIngreso.BuscarIngresosTotales(1L)).thenReturn(1000.0);

        // Ejecución de la solicitud HTTP
        mockMvc.perform(get("/Finanzapp/Ingresos/ingresostotal/{id_usuario}", 1L))
                .andExpect(status().isOk()) // Verifica que el código de estado es 200
                .andExpect(content().string("1000.0")); // Verifica el contenido de la respuesta

        // Verifica que se llamó al servicio
        verify(servicioIngreso, times(1)).BuscarIngresosTotales(1L);
    }

    @Test
    public void testObtenerTotalIngresos_Vacio() throws Exception {
        // Configuración del mock
        when(servicioIngreso.BuscarIngresosTotales(1L)).thenReturn(0.0);

        // Ejecución de la solicitud HTTP
        mockMvc.perform(get("/Finanzapp/Ingresos/ingresostotal/{id_usuario}", 1L))
                .andExpect(status().isNoContent()); // Verifica que el código de estado es 204

        // Verifica que se llamó al servicio
        verify(servicioIngreso, times(1)).BuscarIngresosTotales(1L);
    }

    @Test
    public void testEliminarIngreso() throws Exception {
        // Ejecución de la solicitud HTTP
        mockMvc.perform(delete("/Finanzapp/Ingresos/EliminarIngresos/{id_ingreso}", 1L))
                .andExpect(status().isNoContent()); // Verifica que el código de estado es 204

        // Verifica que se llamó al servicio
        verify(servicioIngreso, times(1)).eliminarIngreso(1L);
    }
}
