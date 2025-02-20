package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.AlcanciaDTO;
import com.example.FinanzApp.DTOS.DepositoDTO;
import com.example.FinanzApp.Servicios.ServicioDeposito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Finanzapp/Deposito")
public class ControladorDeposito {

    @Autowired
    ServicioDeposito servicioDeposito;

    @PostMapping("/RegistrarDeposito/{id_usuario}/{id_alcancia}")
    public ResponseEntity<DepositoDTO> registrarGasto(@RequestBody DepositoDTO depositoDTO, @PathVariable Long id_usuario , @PathVariable Long id_alcancia) {

        DepositoDTO depositoregistrado = servicioDeposito.realizarDeposito(depositoDTO, id_usuario , id_alcancia);

        if (depositoregistrado  != null) {
            return ResponseEntity.ok(depositoregistrado);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
    @GetMapping("/ObtenerDepositos/{id_alcancia}")
    public ResponseEntity<List<DepositoDTO>> BuscarPorID(@PathVariable Long id_alcancia) {

        List<DepositoDTO> depositos = servicioDeposito.ObtenerDepositos(id_alcancia);

        if (depositos != null) {
            return ResponseEntity.ok(depositos);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ObtenerValorGastosMesDeposito/{id_usuario}")
    public ResponseEntity<Double> ObtenerValorGeneral(@PathVariable Long id_usuario) {

        Double ValorGeneral = servicioDeposito.ObtenerValorGastosMesDepositos(id_usuario);

        if (ValorGeneral != null) {
            return ResponseEntity.ok(ValorGeneral);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
}
