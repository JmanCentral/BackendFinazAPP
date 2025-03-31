package com.example.finanzApp.controllers;

import com.example.finanzApp.DTOS.TipsDTO;
import com.example.finanzApp.Servicios.ServicioTips;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Tag(name = "Tips Financieros", description = "API para obtener consejos financieros personalizados.")
@RestController
@RequestMapping("/Finanzapp/Tips/")
@CrossOrigin(origins = "*")
public class ControladorTips {


    private ServicioTips servicioTips;

    @Operation(summary = "Obtener tips financieros", description = "Recupera una lista de consejos financieros personalizados para un usuario específico.")
    @GetMapping("ObtenerTips/{usuarioId}")
    public ResponseEntity<List<TipsDTO>> obtenerTips(@PathVariable Long usuarioId) {
        List<TipsDTO> tips = servicioTips.obtenerConsejosFinancieros(usuarioId);
        return ResponseEntity.ok(tips);
    }
}