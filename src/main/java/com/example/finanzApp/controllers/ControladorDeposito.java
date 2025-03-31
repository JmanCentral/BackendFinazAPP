package com.example.finanzApp.controllers;

import com.example.finanzApp.DTOS.DepositoDTO;
import com.example.finanzApp.Servicios.ServicioDeposito;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Tag(name = "Deposito", description = "API para gestionar depósitos en FinanzApp")
@RestController
@RequestMapping("/Finanzapp/Deposito")
public class ControladorDeposito {


    private final ServicioDeposito servicioDeposito;


    @Operation(summary = "Registrar un nuevo depósito", description = "Permite registrar un depósito en una alcancía asociada a un usuario.")
    @PostMapping("/RegistrarDeposito/{id_usuario}/{id_alcancia}")
    public ResponseEntity<DepositoDTO> registrarDeposito(@RequestBody DepositoDTO depositoDTO, @PathVariable Long id_usuario, @PathVariable Long id_alcancia) {
        DepositoDTO depositoRegistrado = servicioDeposito.realizarDeposito(depositoDTO, id_usuario, id_alcancia);
        if (depositoRegistrado != null) {
            return ResponseEntity.ok(depositoRegistrado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Obtener depósitos de una alcancía", description = "Recupera todos los depósitos asociados a una alcancía específica.")
    @GetMapping("/ObtenerDepositos/{id_alcancia}")
    public ResponseEntity<List<DepositoDTO>> obtenerDepositos(@PathVariable Long id_alcancia) {
        List<DepositoDTO> depositos = servicioDeposito.ObtenerDepositos(id_alcancia);
        if (depositos != null) {
            return ResponseEntity.ok(depositos);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Obtener total de depósitos del mes", description = "Calcula el valor total de los depósitos realizados por un usuario en el mes actual.")
    @GetMapping("/ObtenerValorGastosMesDeposito/{id_usuario}")
    public ResponseEntity<Double> obtenerValorGeneral(@PathVariable Long id_usuario) {
        Double valorGeneral = servicioDeposito.ObtenerValorGastosMesDepositos(id_usuario);
        if (valorGeneral != null) {
            return ResponseEntity.ok(valorGeneral);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Modificar un depósito", description = "Permite actualizar la información de un depósito existente en una alcancía.")
    @PutMapping("/ModificarDepositos/{id_deposito}/{id_alcancia}")
    public ResponseEntity<DepositoDTO> modificarDeposito(@RequestBody DepositoDTO depositoDTO, @PathVariable Long id_deposito, @PathVariable Long id_alcancia) {
        DepositoDTO depositoModificado = servicioDeposito.ModificarDeposito(depositoDTO, id_deposito, id_alcancia);
        if (depositoModificado != null) {
            return ResponseEntity.ok(depositoModificado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Eliminar un depósito", description = "Elimina un depósito específico de una alcancía.")
    @DeleteMapping("/EliminarDeposito/{id_deposito}/{id_alcancia}")
    public ResponseEntity<Void> eliminarDeposito(@PathVariable Long id_deposito, @PathVariable Long id_alcancia) {
        servicioDeposito.EliminarDeposito(id_deposito, id_alcancia);
        return ResponseEntity.noContent().build();
    }
}
