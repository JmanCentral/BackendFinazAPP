package com.example.finanzApp.controllers;

import com.example.finanzApp.DTOS.AlertaDTO;
import com.example.finanzApp.Servicios.ServicioAlerta;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;

@AllArgsConstructor
@RestController
@RequestMapping("/Finanzapp/Alertas")
@Tag(name = "Alertas", description = "Endpoints para la gestión de alertas del usuario")
public class ControladorAlerta {


    private final ServicioAlerta servicioAlerta;

    @Operation(summary = "Registrar una nueva alerta para un usuario")
    @PostMapping("/RegistrarAlerta/{id_usuario}")
    public ResponseEntity<AlertaDTO> registrarAlerta(@RequestBody AlertaDTO alerta, @PathVariable Long id_usuario) {
        AlertaDTO alertaRegistrada = servicioAlerta.RegistrarAlerta(alerta, id_usuario);
        return (alertaRegistrada != null) ? ResponseEntity.ok(alertaRegistrada) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Obtener alertas de un usuario")
    @GetMapping("/ObtenerAlertaPorUser/{id_usuario}")
    public ResponseEntity<List<AlertaDTO>> listarAlertaPorUser(@PathVariable Long id_usuario) {
        List<AlertaDTO> alertas = servicioAlerta.ObtenerAlerta(id_usuario);
        return ResponseEntity.ok(alertas);
    }

    @Operation(summary = "Obtener alertas de un usuario por año")
    @GetMapping("/ObtenerAlertaPorAnio/{id_usuario}")
    public ResponseEntity<List<AlertaDTO>> listarAlertaPorAnio(@PathVariable Long id_usuario) {
        List<AlertaDTO> alertas = servicioAlerta.ObtenerAlertaFecha(id_usuario);
        return ResponseEntity.ok(alertas);
    }

    @Operation(summary = "Obtener alertas de un usuario por mes")
    @GetMapping("/ObtenerAlertaPorMes/{id_usuario}")
    public ResponseEntity<List<AlertaDTO>> listarAlertaPorMes(@PathVariable Long id_usuario) {
        List<AlertaDTO> alertas = servicioAlerta.ObtenerAlertaEsteMes(id_usuario);
        return ResponseEntity.ok(alertas);
    }

    @Operation(summary = "Modificar una alerta existente")
    @PutMapping("/ModificarAlerta/{id_alerta}")
    public ResponseEntity<AlertaDTO> modificarAlerta(@PathVariable Long id_alerta, @RequestBody AlertaDTO alertaDTO) {
        AlertaDTO alertaModificada = servicioAlerta.ModificarAlerta(id_alerta, alertaDTO);
        return (alertaModificada != null) ? ResponseEntity.ok(alertaModificada) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Eliminar una alerta por ID")
    @DeleteMapping("/EliminarAlertas/{id_alerta}")
    public ResponseEntity<Void> eliminarAlerta(@PathVariable Long id_alerta) {
        servicioAlerta.EliminarAlerta(id_alerta);
        return ResponseEntity.noContent().build();
    }
}
