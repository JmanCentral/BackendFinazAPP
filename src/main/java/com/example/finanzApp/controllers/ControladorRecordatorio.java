package com.example.finanzApp.controllers;

import com.example.finanzApp.DTOS.RecordatorioDTO;
import com.example.finanzApp.Servicios.ServicioRecordatorio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping("/Finanzapp/Recordatorios")
@Tag(name = "Recordatorios", description = "Operaciones relacionadas con los recordatorios de los usuarios")
public class ControladorRecordatorio {


    private final ServicioRecordatorio servicioRecordatorio;

    @PostMapping("/registro/Recordatorio/{id_usuario}")
    @Operation(summary = "Registrar un recordatorio", description = "Registra un nuevo recordatorio asociado a un usuario.")
    public ResponseEntity<RecordatorioDTO> registrarUsuario(@RequestBody RecordatorioDTO recordatorioDTO, @PathVariable Long id_usuario) {
        RecordatorioDTO recordatorioInsertado = servicioRecordatorio.RegistrarRecordatorio(recordatorioDTO, id_usuario);
        if (recordatorioInsertado != null) {
            return ResponseEntity.ok(recordatorioInsertado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/ObtenerRecordatorios/{id_usuario}")
    @Operation(summary = "Listar recordatorios", description = "Obtiene la lista de recordatorios de un usuario.")
    public ResponseEntity<List<RecordatorioDTO>> ListarAlertaPorMes(@PathVariable Long id_usuario) {
        List<RecordatorioDTO> recordatorioConsultado = servicioRecordatorio.ListarRecordatorios(id_usuario);
        if (!recordatorioConsultado.isEmpty()) {
            return ResponseEntity.ok(recordatorioConsultado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/modificar/Recordatorio/{id_recordatorio}")
    @Operation(summary = "Modificar un recordatorio", description = "Modifica los detalles de un recordatorio existente.")
    public ResponseEntity<RecordatorioDTO> modificarRecordatorio(
            @PathVariable Long id_recordatorio,
            @RequestBody RecordatorioDTO recordatorioDTO) {
        try {
            RecordatorioDTO recordatorioActualizado = servicioRecordatorio.ModificarRecordatorio(id_recordatorio, recordatorioDTO);
            return ResponseEntity.ok(recordatorioActualizado);
        } catch (RuntimeException e) {
            log.error("Error al modificar el recordatorio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/EliminarRecordatorios/{id_recordatorio}")
    @Operation(summary = "Eliminar un recordatorio", description = "Elimina un recordatorio específico por su ID.")
    public ResponseEntity<Void> eliminarRecordatorio(@PathVariable("id_recordatorio") Long id_recordatorio) {
        servicioRecordatorio.EliminarRecordatorio(id_recordatorio);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminartodos/{id_usuario}")
    @Operation(summary = "Eliminar todos los recordatorios", description = "Elimina todos los recordatorios de un usuario específico.")
    public ResponseEntity<String> eliminarTodos(@PathVariable Long id_usuario) {
        servicioRecordatorio.eliminarTodosLosRecordatorios(id_usuario);
        return ResponseEntity.ok("Todos los recordatorios han sido eliminados.");
    }

    @GetMapping("/BuscarPorNombre/{nombre}")
    @Operation(summary = "Buscar recordatorios por nombre", description = "Obtiene una lista de recordatorios que coinciden con el nombre especificado.")
    public ResponseEntity<List<RecordatorioDTO>> BuscarPorNombre(@PathVariable String nombre) {
        List<RecordatorioDTO> recordatorioDTO = servicioRecordatorio.BuscarPorNombre(nombre);
        if (recordatorioDTO != null) {
            return ResponseEntity.ok(recordatorioDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
