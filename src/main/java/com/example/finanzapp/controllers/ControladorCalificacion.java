package com.example.finanzapp.controllers;

import com.example.finanzapp.DTOS.CalificacionDTO;
import com.example.finanzapp.Servicios.ServicioCalificacion;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Calificaciones", description = "API para gestionar calificaciones en FinanzApp")
@RequestMapping("/Finanzapp/calificaciones")

public class ControladorCalificacion {

    //Inyección de dependencias
    private final ServicioCalificacion servicioCalificacion;

    @PostMapping("/RegistrarCalificacion")
    public  ResponseEntity<CalificacionDTO> registrarCalificacion(@RequestBody CalificacionDTO calificacionDTO) {

       CalificacionDTO  nuevacalificacion =  servicioCalificacion.registrarCalificacion(calificacionDTO);

       if (nuevacalificacion != null) {
           return ResponseEntity.ok(nuevacalificacion);
       }else {
          return ResponseEntity.badRequest().body(null);
       }
    }

    @GetMapping("/ObtenerCalificaciones")
    public ResponseEntity<List<CalificacionDTO>> obtenerCalificaciones() {
        List<CalificacionDTO> calificaciones = servicioCalificacion.listarCalificaciones();
        if (calificaciones != null) {
            return ResponseEntity.ok(calificaciones);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
