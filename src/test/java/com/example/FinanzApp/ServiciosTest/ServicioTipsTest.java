package com.example.FinanzApp.ServiciosTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.FinanzApp.Components.GeminiAdapter;
import com.example.FinanzApp.Config.APIgemini;
import com.example.FinanzApp.DTOS.TipsDTO;
import com.example.FinanzApp.Entidades.Gasto;
import com.example.FinanzApp.Repositorios.RepositorioGasto;
import com.example.FinanzApp.Servicios.ServicioTips;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

class ServicioTipsTest {

    @Mock
    private APIgemini apiGemini;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RepositorioGasto gastoRepository;

    @Mock
    private GeminiAdapter geminiAdapter;

    @InjectMocks
    private ServicioTips servicioTips;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerConsejosFinancieros() {
        // Datos de prueba
        Long usuarioId = 1L;
        Gasto gasto1 = new Gasto(1L, "Comida", "Almuerzo", null, 10000.0, null);
        Gasto gasto2 = new Gasto(2L, "Transporte", "Taxi", null, 5000.0, null);
        List<Gasto> gastos = Arrays.asList(gasto1, gasto2);

        // Mocking
        when(gastoRepository.findGastosByUsuarioId(usuarioId)).thenReturn(gastos);
        when(apiGemini.getApiKey()).thenReturn("testApiKey");

        JsonNode mockResponse = mock(JsonNode.class);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(JsonNode.class)))
                .thenReturn(ResponseEntity.ok(mockResponse));

        // Simular la conversión de JSON a TipsDTO usando mockStatic
        List<TipsDTO> tipsDTOList = Arrays.asList(new TipsDTO("Consejo 1", "Buena bro"), new TipsDTO("Consejo 2", "Buena bro"));

        try (MockedStatic<GeminiAdapter> mockedStatic = mockStatic(GeminiAdapter.class)) {
            mockedStatic.when(() -> GeminiAdapter.convertirDesdeJson(mockResponse)).thenReturn(tipsDTOList);

            // Llamar al método
            List<TipsDTO> result = servicioTips.obtenerConsejosFinancieros(usuarioId);

            // Verificaciones
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("Buena bro", result.get(0).getContenido());
            assertEquals("Buena bro", result.get(1).getContenido());

            // Verificar que se llamaron los métodos esperados
            verify(gastoRepository).findGastosByUsuarioId(usuarioId);
            verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(JsonNode.class));
        }
    }
}

