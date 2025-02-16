package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.AlcanciaDTO;
import com.example.FinanzApp.DTOS.DepositoDTO;
import com.example.FinanzApp.Servicios.ServicioDeposito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
