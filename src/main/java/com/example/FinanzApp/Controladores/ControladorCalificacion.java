package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.CalificacionDTO;
import com.example.FinanzApp.DTOS.DepositoDTO;
import com.example.FinanzApp.Servicios.ServicioCalificacion;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Calificaciones", description = "API para gestionar calificaciones en FinanzApp")
@RequestMapping("/Finanzapp/calificaciones")

public class ControladorCalificacion {

    @Autowired
    ServicioCalificacion servicioCalificacion;

    @PostMapping("/RegistrarCalificacion")
    public  ResponseEntity<CalificacionDTO> registrarCalificacion(@RequestBody CalificacionDTO calificacionDTO) {

       CalificacionDTO  nuevacalificacion =  servicioCalificacion.registrarCalificacion(calificacionDTO);

       if (nuevacalificacion != null) {
           return ResponseEntity.ok(nuevacalificacion);
       }else {
          return ResponseEntity.badRequest().body(null);
       }
    }

    @GetMapping("/ObtenerCalificaciones/{id_consejo}")
    public ResponseEntity<List<CalificacionDTO>> obtenerCalificaciones(@PathVariable Long id_consejo) {
        List<CalificacionDTO> calificaciones = servicioCalificacion.listarCalificaciones(id_consejo);
        if (calificaciones != null) {
            return ResponseEntity.ok(calificaciones);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
