package com.example.finanzapp.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class APIgemini {

    @Value("${gemini.api.key}")
    private String apiKey;

}
