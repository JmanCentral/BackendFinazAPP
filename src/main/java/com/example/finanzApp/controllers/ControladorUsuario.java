package com.example.finanzApp.controllers;

import com.example.finanzApp.DTOS.UsuarioDTO;
import com.example.finanzApp.Servicios.ServicioUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping("/Finanzapp")
@Tag(name = "Usuarios", description = "Endpoints para la gestión de usuarios")
public class ControladorUsuario {


    private final ServicioUsuario servicioUsuario;

    @Operation(summary = "Registrar un nuevo usuario", description = "Registra un usuario en la base de datos y devuelve su información")
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioDTO usuario) {

        UsuarioDTO usuarioinsertado = servicioUsuario.registrarUsuario(usuario);

        if (usuarioinsertado != null) {
            return ResponseEntity.ok(usuarioinsertado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Obtener un usuario por ID", description = "Obtiene la información de un usuario basado en su ID")
    @GetMapping("/ObtenerUsuario/{id_usuario}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id_usuario) {

        UsuarioDTO usuario = servicioUsuario.obtenerUusarioPorID(id_usuario);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    }

