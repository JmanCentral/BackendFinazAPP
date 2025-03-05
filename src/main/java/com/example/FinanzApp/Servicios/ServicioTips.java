package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.Config.APIgemini;
import com.example.FinanzApp.DTOS.TipsDTO;
import com.example.FinanzApp.Entidades.Gasto;
import com.example.FinanzApp.Repositorios.RepositorioGasto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ServicioTips {

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    private final APIgemini apiGemini;

    private final RestTemplate restTemplate;
    private final RepositorioGasto gastoRepository;


    public List<TipsDTO> obtenerConsejosFinancieros(Long usuarioId) {
        List<Gasto> gastos = gastoRepository.findGastosByUsuarioId(usuarioId);
        String prompt = generarPrompt(gastos);

        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";
        String url = GEMINI_API_URL + apiGemini.getApiKey();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, requestEntity, JsonNode.class);

        return procesarRespuesta(Objects.requireNonNull(responseEntity.getBody()));
    }

    private String formatearGasto(Gasto gasto) {
        return String.format("%s: %.2f COP, %s, ", gasto.getCategoria(), gasto.getValor(), gasto.getNombre_gasto());
    }

    private String generarPrompt(List<Gasto> gastos) {
        StringBuilder prompt = new StringBuilder("Analiza los siguientes gastos y genera 5 consejos financieros personalizados: ");
        for (Gasto gasto : gastos) {
            prompt.append(formatearGasto(gasto));
        }
        prompt.append("Devuelve los consejos en formato JSON con claves consejo1, consejo2, ..., consejo5.");
        return prompt.toString();
    }

    private List<TipsDTO> procesarRespuesta(JsonNode body) {
        List<TipsDTO> consejos = new ArrayList<>();

        try {
            // Extraer el texto de la respuesta
            String textResponse = body.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();

            // Limpiar la respuesta para quitar los delimitadores ```json\n y \n```
            String jsonClean = textResponse.replaceAll("```json\\n|\\n```", "");

            // Convertir la cadena JSON a un objeto JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode consejosJson = objectMapper.readTree(jsonClean);

            // Recorrer el JSON y mapearlo a TipsDTO
            consejosJson.fields().forEachRemaining(entry -> {
                consejos.add(new TipsDTO(entry.getKey(), entry.getValue().asText()));
            });

        } catch (Exception e) {
            System.err.println("Error procesando la respuesta de la API: " + e.getMessage());
        }

        return consejos;
    }
}
