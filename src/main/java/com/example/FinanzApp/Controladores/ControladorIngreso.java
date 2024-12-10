package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.DTOS.UsuarioDTO;
import com.example.FinanzApp.Servicios.ServicioIngreso;
import com.example.FinanzApp.Servicios.ServicioUsuario;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/Finanzapp")
public class ControladorIngreso {

    @Autowired
    ServicioIngreso servicioIngreso;

    @PostMapping("/registrarIngreso/{id_usuario}")
    public ResponseEntity<IngresoDTO> registrarUsuario(@RequestBody IngresoDTO ingreso , @PathVariable Long id_usuario) {

        IngresoDTO ingresoInsertado = servicioIngreso.RegistrarIngreso(ingreso , id_usuario);

        if (ingresoInsertado != null) {
            return ResponseEntity.ok(ingresoInsertado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/IngresosMensuales/{id_usuario}")
    public ResponseEntity<List<IngresoDTO>> listarIngresos(@PathVariable Long id_usuario) {

        List<IngresoDTO> ingresos = servicioIngreso.BuscarIngresosMensuales(id_usuario);

        if (ingresos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ingresos);
    }

}
