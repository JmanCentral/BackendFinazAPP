package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.RecordatorioDTO;
import com.example.FinanzApp.Servicios.ServicioRecordatorio;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/Finanzapp/Recordatorios")

public class ControladorRecordatorio {

    @Autowired
    ServicioRecordatorio servicioRecordatorio;

    @PostMapping("/registro/Recordatorio/{id_usuario}")
    public ResponseEntity<RecordatorioDTO> registrarUsuario(@RequestBody RecordatorioDTO recordatorioDTO , @PathVariable Long id_usuario ) {

        RecordatorioDTO recordatorioinsertado = servicioRecordatorio.RegistrarRecordatorio(recordatorioDTO , id_usuario);

        if (recordatorioinsertado  != null) {
            return ResponseEntity.ok(recordatorioinsertado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/ObtenerRecordatorios/{id_usuario}")
    public ResponseEntity <List<RecordatorioDTO>> ListarAlertaPorMes(@PathVariable Long id_usuario) {

        List <RecordatorioDTO> RecordatorioConsultado = servicioRecordatorio.ListarRecordatorios(id_usuario);

        if (!RecordatorioConsultado.isEmpty()) {
            return ResponseEntity.ok(RecordatorioConsultado);
        } else {
            return ResponseEntity.badRequest().build();

        }

    }

    // Endpoint para modificar un recordatorio existente
    @PutMapping("/modificar/Recordatorio/{id_recordatorio}")
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
    public ResponseEntity<Void> eliminarRecordatorio(@PathVariable("id_recordatorio") Long id_recordatorio) {
        servicioRecordatorio.EliminarRecordatorio(id_recordatorio);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminartodos")
    public ResponseEntity<String> eliminarTodos() {
        servicioRecordatorio.eliminarTodosLosRecordatorios();
        return ResponseEntity.ok("Todos los recordatorios han sido eliminados.");
    }

    @GetMapping("/BuscarPorNombre/{nombre}")
    public ResponseEntity<RecordatorioDTO> BuscarPorNombre(@PathVariable String nombre ) {

        RecordatorioDTO recordatorioDTO = servicioRecordatorio.BuscarPorNombre(nombre);

        if (recordatorioDTO != null) {
            return ResponseEntity.ok(recordatorioDTO);
        } else {
            return ResponseEntity.badRequest().build();

        }

    }


}
