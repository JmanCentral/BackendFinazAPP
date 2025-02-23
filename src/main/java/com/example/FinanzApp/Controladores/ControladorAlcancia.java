package com.example.FinanzApp.Controladores;
import com.example.FinanzApp.DTOS.AlcanciaDTO;
import com.example.FinanzApp.DTOS.GastoDTO;
import com.example.FinanzApp.Servicios.ServicioAlcancia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Finanzapp/Alcancias")
public class ControladorAlcancia {

    @Autowired
    ServicioAlcancia servicioAlcancia;

    @PostMapping("/RegistrarAlcancia/{id_usuario}")
    public ResponseEntity<AlcanciaDTO> registrarGasto(@RequestBody AlcanciaDTO alcancia, @PathVariable Long id_usuario) {

        AlcanciaDTO AlcanciaRegistrada = servicioAlcancia.crearAlcancia(alcancia, id_usuario);

        if (AlcanciaRegistrada != null) {
            return ResponseEntity.ok(AlcanciaRegistrada);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/BuscarPorCodigo/{codigo}")
    public ResponseEntity<List<AlcanciaDTO>> BuscarPorCodigo(@PathVariable String codigo) {

        List<AlcanciaDTO> alcancia = servicioAlcancia.buscarAlcancia(codigo);
        if (alcancia != null) {
            return ResponseEntity.ok(alcancia);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/BuscarAlcancias/{id_usuario}")
    public ResponseEntity<List<AlcanciaDTO>> BuscarPorID(@PathVariable Long id_usuario) {

        List<AlcanciaDTO> alcancia = servicioAlcancia.buscarAlcanciasporUser(id_usuario);

        if (alcancia != null) {
            return ResponseEntity.ok(alcancia);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/ModificarAlcancias/{id_alcancia}")
    public ResponseEntity<AlcanciaDTO> modificarGasto(@RequestBody AlcanciaDTO alcancia, @PathVariable Long id_alcancia) {

        AlcanciaDTO alcanciaregistrada = servicioAlcancia.ModificarAlcancia(alcancia , id_alcancia);

        if (alcanciaregistrada != null) {
            return ResponseEntity.ok(alcanciaregistrada);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/EliminarAlcancia/{id_alcancia}")
    public ResponseEntity<Void> eliminarAlcancia(@PathVariable("id_alcancia") Long idAlcancia) {
        servicioAlcancia.eliminarAlcancia(idAlcancia);
        return ResponseEntity.noContent().build();
    }

}
