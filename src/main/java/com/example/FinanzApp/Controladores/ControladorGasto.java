package com.example.FinanzApp.Controladores;
import com.example.FinanzApp.DTOS.GastoDTO;
import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.Servicios.ServicioGasto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/Finanzapp/Gastos")
public class ControladorGasto {

    @Autowired
    ServicioGasto servicioGasto;

    @PostMapping("/RegistrarGasto/{id_usuario}")
    public ResponseEntity<GastoDTO> registrarGasto(@RequestBody GastoDTO gasto, @PathVariable Long id_usuario) {

        GastoDTO gastoregistrado = servicioGasto.RegistrarGasto(gasto, id_usuario);

        if (gastoregistrado != null) {
            return ResponseEntity.ok(gastoregistrado);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerDineroDisponible/{id_usuario}")

    public ResponseEntity<Double> ObtenerMoneyDispobnible(@PathVariable Long id_usuario) {
        Double Disponible = servicioGasto.ObtenerDisponible(id_usuario);

        if (Disponible != null) {
            return ResponseEntity.ok(Disponible);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/GastosMesCategoria/{id_usuario}/{categoria}")
    public ResponseEntity <List<GastoDTO>> obtenerGastosMesCategoria(@PathVariable Long id_usuario, @PathVariable String categoria) {

        List<GastoDTO>  gastos = servicioGasto.BuscarGastosMesCategoria(id_usuario, categoria);
        if (gastos != null) {
            return ResponseEntity.ok(gastos);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerValorGastosMesCategoria/{id_usuario}/{categoria}")

    public ResponseEntity<Double> ObtenerValorGeneral(@PathVariable Long id_usuario , @PathVariable String categoria) {

        Double ValorGeneral = servicioGasto.ObtenerValorGastosMesCategoria(id_usuario, categoria);

        if (ValorGeneral != null) {
            return ResponseEntity.ok(ValorGeneral);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }






}
