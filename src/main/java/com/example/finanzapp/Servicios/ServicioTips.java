package com.example.finanzapp.Servicios;

import com.example.finanzapp.Components.GeminiAdapter;
import com.example.finanzapp.Config.APIgemini;
import com.example.finanzapp.DTOS.TipsDTO;
import com.example.finanzapp.Entidades.Gasto;
import com.example.finanzapp.Repositorios.RepositorioGasto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ServicioTips {

    // URL base de la API de Gemini usada para generar contenido. Se le concatena la clave de API al final.
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    //Inyección de dependencias
    private final APIgemini apiGemini;
    private final RestTemplate restTemplate; //Cliente HTTP utilizado para enviar peticiones POST a la API de Gemini.
    private final RepositorioGasto gastoRepository; // utilizar el CRUD de la entidad del gasto
    private final GeminiAdapter geminiAdapter;

    public List<TipsDTO> obtenerConsejosFinancieros(Long usuarioId) {
        List<Gasto> gastos = gastoRepository.findGastosByUsuarioId(usuarioId);
        String prompt = generarPrompt(gastos);

        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";
        String url = GEMINI_API_URL + apiGemini.getApiKey();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, requestEntity, JsonNode.class);

        log.info("Respuesta cruda de la API: {}", Objects.requireNonNull(responseEntity.getBody()).toPrettyString());

        return GeminiAdapter.convertirDesdeJson(Objects.requireNonNull(responseEntity.getBody()));
    }

    private String formatearGasto(Gasto gasto) {
        return String.format("%s: %.2f COP, %s, ", gasto.getCategoria(), gasto.getValor(), gasto.getNombre_gasto());
    }

    private String generarPrompt(List<Gasto> gastos) {
        StringBuilder prompt = new StringBuilder("Analiza los siguientes gastos y genera 5 exactamente  consejos financieros personalizados: ");
        for (Gasto gasto : gastos) {
            prompt.append(formatearGasto(gasto));
        }
        prompt.append("\nNo incluyas introducción, explicaciones adicionales ni información extra. Solo devuelve la lista de consejos en el formato indicado.");
        prompt.append("\nFormato de salida estricto:\n");
        prompt.append("- Consejo 1: [Aquí el primer consejo]\n");
        prompt.append("- Consejo 2: [Aquí el segundo consejo]\n");
        prompt.append("- Consejo 3: [Aquí el tercer consejo]\n");
        prompt.append("- Consejo 4: [Aquí el cuarto consejo]\n");
        prompt.append("- Consejo 5: [Aquí el quinto consejo]\n");
        return prompt.toString();
    }
}

