package com.example.FinanzApp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.FinanzApp.Config.APIgemini;
import com.example.FinanzApp.DTOS.GastoDTO;
import com.example.FinanzApp.DTOS.TipsDTO;
import com.example.FinanzApp.Entidades.Gasto;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioGasto;
import com.example.FinanzApp.Servicios.ServicioTips;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;



@ExtendWith(MockitoExtension.class)
public class ServicioTipsTest {

    @Mock
    private RestTemplate restTemplate; // Mock del RestTemplate

    @Mock
    private RepositorioGasto gastoRepository; // Mock del repositorio de gastos

    @Mock
    private APIgemini apiGemini; // Mock de la clase APIgemini

    @InjectMocks
    private ServicioTips servicioTips; // Inyecta los mocks en el servicio

    private Gasto gasto;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        when(apiGemini.getApiKey()).thenReturn("tu_api_key"); // Simula la clave de la API

        // Crear un gasto de prueba
        gasto = new Gasto();
        gasto.setId_gasto(1L);
        gasto.setNombre_gasto("Comida");
        gasto.setCategoria("Alimentación");
        gasto.setFecha(LocalDate.of(2023, 10, 1));
        gasto.setValor(50.0);

        // Crear un usuario de prueba
        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setUsername("testuser");
    }

    @Test
    public void testObtenerConsejosFinancieros() throws Exception {
        // Configuración de los mocks
        List<Gasto> gastos = new ArrayList<>();
        gastos.add(new Gasto(1L, "Comida", "Alimentación", LocalDate.of(2023, 10, 1), 150.0, null));
        gastos.add(new Gasto(2L, "Transporte", "Transporte", LocalDate.of(2023, 10, 2), 50.0, null));

        when(gastoRepository.findGastosByUsuarioId(1L)).thenReturn(gastos); // Simula la respuesta del repositorio

        // Simula la respuesta de la API externa
        String jsonResponse = """
            {
                "candidates": [
                    {
                        "content": {
                            "parts": [
                                {
                                    "text": "```json\\n{\\"consejo1\\":\\"Ahorra en comida\\",\\"consejo2\\":\\"Reduce gastos en transporte\\"}\\n```"
                                }
                            ]
                        }
                    }
                ]
            }
            """;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(jsonNode, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(JsonNode.class)))
                .thenReturn(responseEntity); // Simula la llamada a la API

        // Ejecución del método a probar
        List<TipsDTO> consejos = servicioTips.obtenerConsejosFinancieros(1L);

        // Verificaciones
        assertNotNull(consejos);
        assertEquals(2, consejos.size());
        assertEquals("consejo1", consejos.get(0).getTitulo());
        assertEquals("Ahorra en comida", consejos.get(0).getContenido());
        assertEquals("consejo2", consejos.get(1).getTitulo());
        assertEquals("Reduce gastos en transporte", consejos.get(1).getContenido());

        // Verifica que se llamó al repositorio y a la API
        verify(gastoRepository, times(1)).findGastosByUsuarioId(1L);
        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(JsonNode.class));
    }
}