package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.ConsejosDTO;
import com.example.FinanzApp.Servicios.GeminiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/gemini")
@Slf4j
@CrossOrigin(origins = "*")
public class ControladorConsejos {

    @Autowired
    GeminiService geminiService;

    @GetMapping("/consejos")
    public Mono<ResponseEntity<ConsejosDTO>> obtenerConsejos() {
        return geminiService.obtenerConsejos()
                .doOnNext(response -> log.info("Respuesta del servicio Gemini: {}", response))
                .map(consejos -> ResponseEntity.ok().body(consejos))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build())
                .doOnError(e -> log.error("Error en obtenerConsejos: {}", e.getMessage()));
    }
}
