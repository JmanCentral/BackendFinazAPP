package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.Entidades.Consejos;
import com.example.FinanzApp.Entidades.Pregunta;
import com.example.FinanzApp.Servicios.ServicioPregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Finazapp/preguntas")
@CrossOrigin
public class ControladorPregunta {

    @Autowired
    ServicioPregunta servicioPregunta;

    @PostMapping("/Registrar")
    public ResponseEntity<String> insertAllConsejos() {

        servicioPregunta.insertAllPreguntas();
        return ResponseEntity.ok("Preguntas insertadas correctamente");

    }

    @GetMapping("/Obtener")
    public ResponseEntity<List<Pregunta>> obtenerConsejos() {

        List<Pregunta> preguntas = servicioPregunta.obtenerpreguntasAleatorios();
        return ResponseEntity.ok(preguntas);
    }


}
