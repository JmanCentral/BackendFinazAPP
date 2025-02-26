package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.TipsDTO;
import com.example.FinanzApp.Servicios.ServicioTips;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tips")
public class ControladorTips {

    @Autowired
    ServicioTips servicioTips;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<TipsDTO>> obtenerTips(@PathVariable Long usuarioId) {
        List<TipsDTO> tips = servicioTips.obtenerConsejosFinancieros(usuarioId);
        return ResponseEntity.ok(tips);
    }
}
