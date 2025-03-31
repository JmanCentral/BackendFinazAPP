package com.example.finanzApp.Components;

import com.example.finanzApp.DTOS.TipsDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class GeminiAdapter {

    public static List<TipsDTO> convertirDesdeJson(JsonNode body) {
        List<TipsDTO> consejos = new ArrayList<>();
        try {
            if (body.has("candidates") && body.get("candidates").isArray() && !body.get("candidates").isEmpty()) {
                JsonNode candidate = body.get("candidates").get(0);
                if (candidate.has("content") && candidate.get("content").has("parts")
                        && candidate.get("content").get("parts").isArray()) {

                    // Extraer el texto completo de la respuesta de Gemini
                    String textResponse = candidate.get("content").get("parts").get(0).get("text").asText();

                    // Dividir el texto en líneas o en sugerencias individuales
                    String[] lineas = textResponse.split("\n");

                    // Convertir cada línea en un objeto TipsDTO (asumiendo formato: "Título: Descripción")
                    for (String linea : lineas) {
                        if (linea.contains(":")) {
                            String[] partes = linea.split(":", 2);
                            consejos.add(new TipsDTO(partes[0].trim(), partes[1].trim()));
                        } else {
                            // Si no tiene ":", asumir que es solo el consejo sin título
                            consejos.add(new TipsDTO("Consejo", linea.trim()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error procesando la respuesta de Gemini: " + e.getMessage());
        }

        return consejos;
    }

}
