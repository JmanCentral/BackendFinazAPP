package com.example.finanzapp.controllers;

import com.example.finanzapp.DTOS.ConsejosDTO;
import com.example.finanzapp.Servicios.ServicioConsejos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Tag(name = "Consejos", description = "API para gestionar consejos financieros en FinanzApp")
@RestController
@RequestMapping("/Finanzapp/Consejos")
@Slf4j
@CrossOrigin(origins = "*")
public class ControladorConsejos {

    //Inyección de dependencias
    private final ServicioConsejos servicioConsejos;

    @Operation(summary = "Registrar consejos", description = "Inserta un conjunto de consejos financieros en la base de datos.")
    @PostMapping("/Registrar")
    public ResponseEntity<ConsejosDTO>  RegistrarConsejos(@RequestBody ConsejosDTO consejosDTO) {

        ConsejosDTO consejoRegistrado = servicioConsejos.RegistrarConsejos(consejosDTO);
        if (consejoRegistrado != null) {
            return ResponseEntity.ok(consejoRegistrado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Obtener consejos", description = "Recupera una lista de consejos financieros aleatorios.")
    @GetMapping("/Obtener")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ConsejosDTO>> obtenerConsejos() {
        List<ConsejosDTO> consejos = servicioConsejos.obtenerConsejosAleatorios();
        log.info("Se han recuperado {} consejos financieros.", consejos.size());
        return ResponseEntity.ok(consejos);
    }
}
