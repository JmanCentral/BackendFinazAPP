package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.Entidades.Consejos;
import com.example.FinanzApp.Servicios.ServicioConsejos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Consejos", description = "API para gestionar consejos financieros en FinanzApp")
@RestController
@RequestMapping("/Finanzapp/Consejos")
@Slf4j
@CrossOrigin(origins = "*")
public class ControladorConsejos {

    @Autowired
    private ServicioConsejos servicioConsejos;

    @Operation(summary = "Registrar consejos", description = "Inserta un conjunto de consejos financieros en la base de datos.")
    @PostMapping("/Registrar")
    public ResponseEntity<String> insertAllConsejos() {
        servicioConsejos.insertAllConsejos();
        log.info("Consejos financieros insertados correctamente.");
        return ResponseEntity.ok("Consejos insertados correctamente");
    }

    @Operation(summary = "Obtener consejos", description = "Recupera una lista de consejos financieros aleatorios.")
    @GetMapping("/Obtener")
    public ResponseEntity<List<Consejos>> obtenerConsejos() {
        List<Consejos> consejos = servicioConsejos.obtenerConsejosAleatorios();
        log.info("Se han recuperado {} consejos financieros.", consejos.size());
        return ResponseEntity.ok(consejos);
    }
}
