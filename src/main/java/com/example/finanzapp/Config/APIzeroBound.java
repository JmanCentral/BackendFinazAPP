package com.example.finanzapp.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class APIzeroBound {

    @Value("${zerobound.api.key}")
    private String apiKeyZerobound;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean esCorreoValido(String email) {
        String url = String.format(
                "https://api.zerobounce.net/v2/validate?api_key=%s&email=%s",
                apiKeyZerobound, email);

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> responseBody = response.getBody();

            String status = (String) responseBody.get("status");
            return "valid".equalsIgnoreCase(status); // solo permitimos emails válidos
        } catch (Exception e) {
            System.err.println("Error llamando a ZeroBounce: " + e.getMessage());
            return false;
        }
    }
}

