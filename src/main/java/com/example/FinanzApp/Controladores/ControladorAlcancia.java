package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.AlcanciaDTO;
import com.example.FinanzApp.Servicios.ServicioAlcancia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/Finanzapp/Alcancias")
public class ControladorAlcancia {

    @Autowired
    private ServicioAlcancia servicioAlcancia;

    @PostMapping("/RegistrarAlcancia/{id_usuario}")
    public ResponseEntity<AlcanciaDTO> registrarAlcancia(
            @RequestParam("nombre_alcancia") String nombreAlcancia,
            @RequestParam("meta") Double meta,
            @RequestParam("saldoActual") Double saldoActual,
            @RequestParam("codigo") String codigo,
            @RequestParam("fechaCreacion") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCreacion,
            @RequestPart("imagen") MultipartFile imagen,
            @PathVariable Long id_usuario) {

        // Crear el DTO con los datos recibidos
        AlcanciaDTO alcanciaDTO = new AlcanciaDTO();
        alcanciaDTO.setNombre_alcancia(nombreAlcancia);
        alcanciaDTO.setMeta(meta);
        alcanciaDTO.setSaldoActual(saldoActual);
        alcanciaDTO.setCodigo(codigo);
        alcanciaDTO.setFechaCreacion(fechaCreacion);

        // Llamar al servicio para crear la alcancía
        AlcanciaDTO alcanciaRegistrada = servicioAlcancia.crearAlcancia(alcanciaDTO, id_usuario, imagen);

        if (alcanciaRegistrada != null) {
            return ResponseEntity.ok(alcanciaRegistrada);
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
}
