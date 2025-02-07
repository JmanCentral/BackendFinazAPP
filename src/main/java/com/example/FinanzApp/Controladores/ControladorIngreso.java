package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.GastoDTO;
import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.Servicios.ServicioIngreso;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/Finanzapp/Ingresos")
public class ControladorIngreso {

    @Autowired
    ServicioIngreso servicioIngreso;

    @PostMapping("/registrarIngreso/{id_usuario}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IngresoDTO> registrarUsuario(@RequestBody IngresoDTO ingreso , @PathVariable Long id_usuario) {

        IngresoDTO ingresoInsertado = servicioIngreso.RegistrarIngreso(ingreso , id_usuario);

        if (ingresoInsertado != null) {
            return ResponseEntity.ok(ingresoInsertado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/IngresosCasualesAnio/{id_usuario}")
    public ResponseEntity<List<IngresoDTO>> listarIngresosCasualesPorAnio(@PathVariable Long id_usuario) {

        List<IngresoDTO> ingresos = servicioIngreso.BuscarIngresosCasualesPorAnio(id_usuario);

        if (ingresos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ingresos);
    }


    @GetMapping("/IngresosMensuales/{id_usuario}")
    public ResponseEntity<List<IngresoDTO>> listarIngresos(@PathVariable Long id_usuario) {

        List<IngresoDTO> ingresos = servicioIngreso.BuscarIngresosMensuales(id_usuario);

        if (ingresos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ingresos);
    }

    @GetMapping("/IngresosCasuales/{id_usuario}")
    public ResponseEntity<List<IngresoDTO>> listarIngresosCasuales(@PathVariable Long id_usuario) {

        List<IngresoDTO> ingresos = servicioIngreso.BuscarIngresosCasuales(id_usuario);

        if (ingresos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ingresos);
    }


    @GetMapping("/ingresostotal/{id_usuario}")
    public ResponseEntity<Double> obtenerTotalIngresos(@PathVariable Long id_usuario) {
        Double totalIngresos = servicioIngreso.BuscarIngresosTotales(id_usuario);

        if (totalIngresos == 0.0) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(totalIngresos);
    }

    @GetMapping("/ingresosmensuales/{id_usuario}/{anio}/{mes}")
    public ResponseEntity<List<IngresoDTO>> getIngresosMensuales(
            @PathVariable("id_usuario") Long usuarioId,
            @PathVariable("anio") Integer anio,
            @PathVariable("mes") Integer mes) {

        // Verifica que los parámetros no sean nulos
        if (usuarioId == null || anio == null || mes == null) {
            return ResponseEntity.badRequest().build(); // Responde con error 400 si hay parámetros nulos
        }

        // Llama al servicio para obtener los ingresos mensuales
        List<IngresoDTO> ingresosMensuales = servicioIngreso.BuscarIngresosMensuales(usuarioId, anio, mes);

        return ResponseEntity.ok(ingresosMensuales); // Devuelve la respuesta con los ingresos encontrados
    }

    @GetMapping("/ProyeccionesIngreso/{id_usuario}")
    public ResponseEntity<Double> Proyecccion(@PathVariable Long id_usuario){

        Double totalIngresos = servicioIngreso.ProyectarIngresos(id_usuario);

        if (totalIngresos == 0.0) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(totalIngresos);

    }


    @PutMapping("/modificar/{id_ingreso}")
    public ResponseEntity<IngresoDTO> modificarIngreso( @PathVariable Long id_ingreso, @RequestBody IngresoDTO ingresoDTO) {

        // Llamamos al servicio para modificar el Ingreso
        IngresoDTO gastoregistrado =   servicioIngreso.ModificarIngreso(id_ingreso , ingresoDTO);

        if (gastoregistrado != null) {
            return ResponseEntity.ok(gastoregistrado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/EliminarIngresos/{id_ingreso}")
    public ResponseEntity<Void> eliminarIngreso(@PathVariable("id_ingreso") Long id_ingreso) {
        servicioIngreso.eliminarIngreso(id_ingreso);
        return ResponseEntity.noContent().build();
    }


}
