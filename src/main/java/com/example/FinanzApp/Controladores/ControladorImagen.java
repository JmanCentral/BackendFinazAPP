package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.ImagenDTO;
import com.example.FinanzApp.Servicios.ServicioImagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/imagenes")
public class ControladorImagen {

    @Autowired
    ServicioImagen servicioImagen;

    @PostMapping("/guardar")
    public ResponseEntity<ImagenDTO> guardarImagen(@RequestParam Long idAlcancia,
                                                   @RequestParam("archivo") MultipartFile archivo) {
        try {
            return ResponseEntity.ok(servicioImagen.guardarImagen(idAlcancia, archivo));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

