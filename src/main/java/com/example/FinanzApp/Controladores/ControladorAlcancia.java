package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.AlcanciaDTO;
import com.example.FinanzApp.Servicios.ServicioAlcancia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Tag(name = "Alcancia", description = "API para gestionar alcancías en FinanzApp")
@RestController
@RequestMapping("/Finanzapp/Alcancias")
public class ControladorAlcancia {

    @Autowired
    private ServicioAlcancia servicioAlcancia;

    @Operation(summary = "Registrar una nueva alcancía", description = "Permite crear una alcancía asociada a un usuario.")
    @PostMapping("/RegistrarAlcancia/{id_usuario}")
    public ResponseEntity<AlcanciaDTO> registrarGasto(@RequestBody AlcanciaDTO alcancia, @PathVariable Long id_usuario) {
        AlcanciaDTO alcanciaRegistrada = servicioAlcancia.crearAlcancia(alcancia, id_usuario);
        if (alcanciaRegistrada != null) {
            return ResponseEntity.ok(alcanciaRegistrada);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar alcancías de un usuario", description = "Obtiene todas las alcancías registradas por un usuario.")
    @GetMapping("/BuscarAlcancias/{id_usuario}")
    public ResponseEntity<List<AlcanciaDTO>> BuscarPorID(@PathVariable Long id_usuario) {
        List<AlcanciaDTO> alcancia = servicioAlcancia.buscarAlcanciasporUser(id_usuario);
        if (alcancia != null) {
            return ResponseEntity.ok(alcancia);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Modificar una alcancía", description = "Permite modificar los datos de una alcancía existente.")
    @PutMapping("/ModificarAlcancias/{id_alcancia}")
    public ResponseEntity<AlcanciaDTO> modificarGasto(@RequestBody AlcanciaDTO alcancia, @PathVariable Long id_alcancia) {
        AlcanciaDTO alcanciaModificada = servicioAlcancia.ModificarAlcancia(alcancia, id_alcancia);
        if (alcanciaModificada != null) {
            return ResponseEntity.ok(alcanciaModificada);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar una alcancía", description = "Elimina una alcancía específica por su ID.")
    @DeleteMapping("/EliminarAlcancia/{id_alcancia}")
    public ResponseEntity<Void> eliminarAlcancia(@PathVariable("id_alcancia") Long idAlcancia) {
        servicioAlcancia.eliminarAlcancia(idAlcancia);
        return ResponseEntity.noContent().build();
    }
}
