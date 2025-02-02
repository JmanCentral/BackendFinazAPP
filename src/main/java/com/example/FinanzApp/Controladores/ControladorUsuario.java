package com.example.FinanzApp.Controladores;

import com.example.FinanzApp.DTOS.UsuarioDTO;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Servicios.ServicioUsuario;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/Finanzapp")
public class ControladorUsuario {

    @Autowired
    ServicioUsuario servicioUsuario;
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioDTO usuario) {

        UsuarioDTO usuarioinsertado = servicioUsuario.registrarUsuario(usuario);

        if (usuarioinsertado != null) {
            return ResponseEntity.ok(usuarioinsertado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PostMapping("/VerificarUsuario")
    public ResponseEntity<UsuarioDTO> verificarUsuario(@RequestBody UsuarioDTO usuarioDTO) {

        UsuarioDTO usuarioVerificado = servicioUsuario.verificarUusario(usuarioDTO.getUsername(), usuarioDTO.getPassword());

        if (usuarioVerificado != null) {
            return ResponseEntity.ok(usuarioVerificado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

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
