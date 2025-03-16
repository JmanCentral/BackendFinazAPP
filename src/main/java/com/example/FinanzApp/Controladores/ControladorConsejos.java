package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.ConsejosDTO;
import com.example.FinanzApp.DTOS.DepositoDTO;
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
    public ResponseEntity<List<ConsejosDTO>> obtenerConsejos() {
        List<ConsejosDTO> consejos = servicioConsejos.obtenerConsejosAleatorios();
        log.info("Se han recuperado {} consejos financieros.", consejos.size());
        return ResponseEntity.ok(consejos);
    }
}
