package com.example.finanzApp.ControladoresTest;

import com.example.finanzApp.DTOS.UsuarioDTO;
import com.example.finanzApp.Servicios.ServicioUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControladorUsuarioTest {

    @Autowired
    private MockMvc mockMvc; // Simula las llamadas HTTP

    @Mock
    private ServicioUsuario servicioUsuario; // Simula el servicio


    @Autowired
    private ObjectMapper objectMapper; // Convierte objetos a JSON

    @Test
    void registrarUsuario_Exitoso() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "user123", "Juan", "juan@example.com", "Perez", "123456", Set.of("USER"));
        when(servicioUsuario.registrarUsuario(usuarioDTO)).thenReturn(usuarioDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/Finanzapp/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_usuario").value(1L))
                .andExpect(jsonPath("$.username").value("user123"))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.apellido").value("Perez"))
                .andExpect(jsonPath("$.contrasena").value("123456"))
                .andExpect(jsonPath("$.roles[0]").value("USER"));
    }

    @Test
    void obtenerUsuario_Exitoso() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "user123", "Juan", "juan@example.com", "Perez", "123456", Set.of("USER"));
        when(servicioUsuario.obtenerUusarioPorID(1L)).thenReturn(usuarioDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/Finanzapp/ObtenerUsuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_usuario").value(1L))
                .andExpect(jsonPath("$.username").value("user123"))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.apellido").value("Perez"))
                .andExpect(jsonPath("$.roles[0]").value("USER"));
    }

    @Test
    void obtenerUsuario_NoEncontrado() throws Exception {
        when(servicioUsuario.obtenerUusarioPorID(99L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/Finanzapp/ObtenerUsuario/99"))
                .andExpect(status().isNotFound()); // Verifica que la respuesta es 404
    }
}
