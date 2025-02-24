package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.ConsejosDTO;
import com.example.FinanzApp.Servicios.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gemini")
public class ControladorConsejos {

    @Autowired
    GeminiService geminiService;

    @GetMapping("/consejos/{id_usuario}")
    public ConsejosDTO obtenerConsejos(@PathVariable Long id_usuario) {
        return geminiService.obtenerConsejos(id_usuario);
    }
}

