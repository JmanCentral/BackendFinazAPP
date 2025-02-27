package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.Entidades.Consejos;
import com.example.FinanzApp.Servicios.ServicioConsejos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Finanzapp/Consejos")
@Slf4j
@CrossOrigin(origins = "*")
public class ControladorConsejos {

    @Autowired
    ServicioConsejos servicioConsejos;

    @PostMapping("/Registrar")
    public ResponseEntity<String> insertAllConsejos() {

        servicioConsejos.insertAllConsejos();
        return ResponseEntity.ok("Preguntas insertadas correctamente");

    }

    @GetMapping("/Obtener")
    public ResponseEntity<List<Consejos>> obtenerConsejos() {

        List<Consejos> consejos = servicioConsejos.obtenerConsejosAleatorios();
        return ResponseEntity.ok(consejos);
    }
}
