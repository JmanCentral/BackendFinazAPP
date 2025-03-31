package com.example.finanzApp.ControladoresTest;

import com.example.finanzApp.DTOS.LoginRequest;
import com.example.finanzApp.Servicios.ServicioLogin;
import com.example.finanzApp.controllers.ControladorLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControladorLogin.class)
@ExtendWith(MockitoExtension.class)
class ControladorLoginTest {

    @Autowired
    private MockMvc mockMvc; // Simula llamadas HTTP

    @Mock
    private ServicioLogin servicioLogin; // Mock del servicio

    @Autowired
    private ObjectMapper objectMapper; // Convierte objetos a JSON

    @Test
    void autenticarUsuario_Exitoso() throws Exception {

        Map<String, Object> fakeResponse = new HashMap<>();
        fakeResponse.put("token", "fake-jwt-token");

        LoginRequest loginRequest = new LoginRequest("user", "password");

        when(servicioLogin.AutenticarUsuario(loginRequest)).thenReturn(fakeResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/Finanzapp/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))) // Convierte el objeto en JSON
                .andExpect(status().isOk()) // Verifica que la respuesta es 200 OK
                .andExpect(jsonPath("$.token").value("fake-jwt-token")); // Verifica el contenido de la respuesta
    }

    @Test
    void autenticarUsuario_FalloAutenticacion() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user", "wrong-password");

        when(servicioLogin.AutenticarUsuario(loginRequest)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/Finanzapp/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()); // Verifica que la respuesta es 401
    }
}
