package com.example.finanzapp.controllers;
import com.example.finanzapp.DTOS.LoginRequest;
import com.example.finanzapp.Servicios.ServicioLogin;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@Tag(name = "Login", description = "Encargado de la autenticación y autorización")
@RequestMapping("/Finanzapp")

public class ControladorLogin {

    //Inyección de dependencias
    private final ServicioLogin servicioLogin;

    @PostMapping("/login")
    public ResponseEntity<?> AutenticarUsuario(@RequestBody LoginRequest loginRequest) {

        Map<String, Object> response = servicioLogin.AutenticarUsuario(loginRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
