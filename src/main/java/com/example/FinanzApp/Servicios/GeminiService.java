package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.Config.APIgemini;
import com.example.FinanzApp.DTOS.ConsejosDTO;
import com.example.FinanzApp.Entidades.Gasto;
import com.example.FinanzApp.Repositorios.RepositorioGasto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GeminiService {

    private final String API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=";

    @Autowired
    private APIgemini apiGemini;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    RepositorioGasto repositorioGasto;


    public ConsejosDTO obtenerConsejos(Long usuarioId) {
        String url = API_URL + apiGemini.getApiKey();

        // 1. Obtener los gastos del usuario
        List<Gasto> gastos = repositorioGasto.findGastosByUsuarioId(usuarioId);

        // 2. Si no hay gastos, retornar DTO vacío con mensaje informativo
        if (gastos.isEmpty()) {
            log.warn("No se encontraron gastos para el usuario con ID {}");
            return new ConsejosDTO(List.of("No hay gastos registrados para generar consejos."));
        }

        // 3. Construir el mensaje con los gastos del usuario
        StringBuilder gastosTexto = new StringBuilder("Mis gastos son:\n");
        for (Gasto gasto : gastos) {
            gastosTexto.append("- ")
                    .append(gasto.getCategoria()).append(": $")
                    .append(gasto.getValor()).append(" (")
                    .append(gasto.getNombre_gasto()).append(")\n");
        }

        // 4. Crear el prompt para la API de Gemini
        String prompt = gastosTexto.toString() +
                "\nDame 10 consejos personalizados para mejorar mis gastos, " +
                "basados en los datos proporcionados de categoría, valor y nombre del gasto de las compras que he hecho. " +
                "Responde en formato de lista numerada del 1 al 10, sin usar : y trata de er coherente en base a los datos que te proporciono.";

        // 5. Construir la solicitud JSON
        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        // 6. Llamar a la API de Gemini
        ResponseEntity<JsonNode> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JsonNode.class);
        } catch (Exception e) {
            log.error("Error al llamar a la API de Gemini: {}");
            return new ConsejosDTO(List.of("No se pudo obtener consejos en este momento."));
        }

        // 7. Procesar la respuesta
        return mapearConsejos(response.getBody());
    }

    private ConsejosDTO mapearConsejos(JsonNode rootNode) {
        JsonNode candidates = rootNode.path("candidates");
        if (!candidates.isArray() || candidates.isEmpty()) {
            return new ConsejosDTO(List.of("No se obtuvieron consejos válidos."));
        }

        JsonNode content = candidates.get(0).path("content").path("parts");
        if (!content.isArray() || content.isEmpty()) {
            return new ConsejosDTO(List.of("No se encontraron consejos en la respuesta."));
        }

        String texto = content.get(0).path("text").asText();
        List<String> consejos = Arrays.stream(texto.split("\n"))
                .map(this::limpiarConsejo)
                .limit(10)
                .toList();

        return new ConsejosDTO(consejos);
    }

    private String limpiarConsejo(String consejo) {
        return consejo.replaceAll("^\\d+\\.\\s*", "").trim();
    }

}
