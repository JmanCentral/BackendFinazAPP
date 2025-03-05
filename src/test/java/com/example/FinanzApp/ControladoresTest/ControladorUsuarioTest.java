package com.example.FinanzApp.ControladoresTest;

import com.example.FinanzApp.DTOS.UsuarioDTO;
import com.example.FinanzApp.Servicios.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;;
import org.springframework.http.MediaType;

import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ControladorUsuarioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioUsuario servicioUsuario;

    private UsuarioDTO usuarioDTO;



    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId_usuario(1L);
        usuarioDTO.setNombre("Juan");
        usuarioDTO.setEmail("juan@example.com");
        usuarioDTO.setUsername("Juan");
        usuarioDTO.setContrasena("12345");
        usuarioDTO.setApellido("Pérez");
        usuarioDTO.setRoles(Set.of("USER"));
        usuarioDTO.setEmail("juan@example.com");
    }

    @Test
    @DisplayName("Prueba registrar usuario - éxito")
    void testRegistrarUsuario() throws Exception {
        Mockito.when(servicioUsuario.registrarUsuario(Mockito.any(UsuarioDTO.class)))
                .thenReturn(usuarioDTO);

        mockMvc.perform(post("/Finanzapp/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_usuario").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.username").value("Juan"))
                .andExpect(jsonPath("$.contrasena").value("12345"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.roles").value("USER"));
    }

    @Test
    @DisplayName("Prueba obtener usuario por ID - éxito")
    void testObtenerUsuarioPorID() throws Exception {
        Mockito.when(servicioUsuario.obtenerUusarioPorID(1L))
                .thenReturn(usuarioDTO);

        mockMvc.perform(get("/Finanzapp/ObtenerUsuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_usuario").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.username").value("Juan"))
                .andExpect(jsonPath("$.contrasena").value("12345"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.roles").value("USER"));
    }

    @Test
    @DisplayName("Prueba obtener usuario por ID - usuario no encontrado")
    void testObtenerUsuarioPorID_NoEncontrado() throws Exception {
        Mockito.when(servicioUsuario.obtenerUusarioPorID(2L))
                .thenReturn(null);

        mockMvc.perform(get("/Finanzapp/ObtenerUsuario/2"))
                .andExpect(status().isNotFound());
    }
}

