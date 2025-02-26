package com.example.FinanzApp.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIgemini {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.key1}")
    private String apiKeyPersonal;

    public String getApiKey() {
        return apiKeyPersonal != null ? apiKeyPersonal : apiKey;
    }


}
