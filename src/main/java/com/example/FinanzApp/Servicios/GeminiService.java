package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.Config.APIgemini;
import com.example.FinanzApp.DTOS.ConsejosDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class GeminiService {

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    private final APIgemini apiGemini;

    private final WebClient.Builder webClientBuilder;

    public Mono<ConsejosDTO> obtenerConsejos() {
        String url = API_URL + apiGemini.getApiKey();

        String prompt = "Dame 10 Consejos que mejoren mis finanzas personales en colombia estructurados. no empieces con aqui tienes 10 consejos .... devuelveme solo los numerales";

        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";

        return webClientBuilder.build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestJson)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnNext(response -> log.info("Respuesta de Gemini API: {}", response.toPrettyString())) // Imprime la respuesta
                .map(this::mapearConsejos)
                .doOnNext(dto -> log.info("DTO mapeado: {}", dto))
                .onErrorResume(e -> {
                    log.error("Error al llamar a la API de Gemini: {}", e.getMessage());
                    return Mono.just(new ConsejosDTO());
                });

    }

    private ConsejosDTO mapearConsejos(JsonNode rootNode) {
        log.info("Iniciando el mapeo de consejos desde la respuesta de la API");

        JsonNode candidates = rootNode.path("candidates");
        if (!candidates.isArray() || candidates.isEmpty()) {
            log.warn("No se encontraron candidatos en la respuesta de la API");
            return new ConsejosDTO();
        }

        JsonNode content = candidates.get(0).path("content").path("parts");
        if (!content.isArray() || content.isEmpty()) {
            log.warn("No se encontró contenido en la respuesta de la API");
            return new ConsejosDTO();
        }

        String texto = content.get(0).path("text").asText();
        log.debug("Texto recibido de la API: {}", texto);

        List<String> consejos = Arrays.stream(texto.split("\n"))
                .map(this::limpiarConsejo)
                .limit(10)
                .toList();

        log.info("Número de consejos obtenidos: {}", consejos.size());

        // Mapear manualmente los consejos al DTO
        ConsejosDTO dto = new ConsejosDTO();
        if (consejos.size() > 0) {
            dto.setConsejo1(consejos.get(0));
            log.debug("Consejo 1: {}", consejos.get(0));
        }
        if (consejos.size() > 1) {
            dto.setConsejo2(consejos.get(1));
            log.debug("Consejo 2: {}", consejos.get(1));
        }
        if (consejos.size() > 2) {
            dto.setConsejo3(consejos.get(2));
            log.debug("Consejo 3: {}", consejos.get(2));
        }
        if (consejos.size() > 3) {
            dto.setConsejo4(consejos.get(3));
            log.debug("Consejo 4: {}", consejos.get(3));
        }
        if (consejos.size() > 4) {
            dto.setConsejo5(consejos.get(4));
            log.debug("Consejo 5: {}", consejos.get(4));
        }
        if (consejos.size() > 5) {
            dto.setConsejo6(consejos.get(5));
            log.debug("Consejo 6: {}", consejos.get(5));
        }
        if (consejos.size() > 6) {
            dto.setConsejo7(consejos.get(6));
            log.debug("Consejo 7: {}", consejos.get(6));
        }
        if (consejos.size() > 7) {
            dto.setConsejo8(consejos.get(7));
            log.debug("Consejo 8: {}", consejos.get(7));
        }
        if (consejos.size() > 8) {
            dto.setConsejo9(consejos.get(8));
            log.debug("Consejo 9: {}", consejos.get(8));
        }
        if (consejos.size() > 9) {
            dto.setConsejo10(consejos.get(9));
            log.debug("Consejo 10: {}", consejos.get(9));
        }

        log.info("Finalizado el mapeo de consejos");
        return dto;
    }

    private String limpiarConsejo(String consejo) {
        return consejo.replaceAll("^\\d+\\.\\s*", "").trim();
    }


}
