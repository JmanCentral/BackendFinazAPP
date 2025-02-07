package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.AlertaDTO;
import com.example.FinanzApp.DTOS.GastoDTO;
import com.example.FinanzApp.Servicios.ServicioAlerta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Finanzapp/Alerta")

public class ControladorAlerta {

    @Autowired
    ServicioAlerta servicioAlerta;

    @PostMapping("/RegistrarAlerta/{id_usuario}")
    public ResponseEntity<AlertaDTO> registrarGasto(@RequestBody AlertaDTO alerta, @PathVariable Long id_usuario) {

        AlertaDTO AlertaRegistrada = servicioAlerta.RegistrarAlerta(alerta, id_usuario);

        if (AlertaRegistrada != null) {
            return ResponseEntity.ok(AlertaRegistrada);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerAlertaPorUser/{id_usuario}")
    public ResponseEntity <List<AlertaDTO>> ListarAlertaPorUser(@PathVariable Long id_usuario) {

        List <AlertaDTO>  AlertaConsultada = servicioAlerta.ObtenerAlerta(id_usuario);

        if (AlertaConsultada.isEmpty()) {
            return ResponseEntity.ok(AlertaConsultada);

        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerAlertaPorAnio/{id_usuario}")
    public ResponseEntity <List<AlertaDTO>> ListarAlertaPorAnio(@PathVariable Long id_usuario) {

        List <AlertaDTO>  AlertaConsultada = servicioAlerta.ObtenerAlertaFecha(id_usuario);

        if (!AlertaConsultada.isEmpty()) {
            return ResponseEntity.ok(AlertaConsultada);

        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerAlertaPorMes/{id_usuario}")
    public ResponseEntity <List<AlertaDTO>> ListarAlertaPorMes(@PathVariable Long id_usuario) {

        List <AlertaDTO>  AlertaConsultada = servicioAlerta.ObtenerAlertaEsteMes(id_usuario);

        if (!AlertaConsultada.isEmpty()) {
            return ResponseEntity.ok(AlertaConsultada);
        } else {
            return ResponseEntity.badRequest().build();

        }

    }

    @PutMapping("/ModificarAlerta/{id_alerta}")
    public ResponseEntity <AlertaDTO> ModificarAlerta(@PathVariable Long id_alerta , AlertaDTO alerta) {

        AlertaDTO  AlertaModificada = servicioAlerta.ModificarAlerta(id_alerta , alerta);

        if (AlertaModificada != null) {
            return ResponseEntity.ok(AlertaModificada);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/EliminarAlertas/{id_alerta}")
    public ResponseEntity<Void> eliminarAlerta(@PathVariable("id_alerta") Long id_alerta) {
        servicioAlerta. EliminarAlerta(id_alerta);
        return ResponseEntity.noContent().build();
    }

}
