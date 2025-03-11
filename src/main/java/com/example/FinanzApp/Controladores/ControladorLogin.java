package com.example.FinanzApp.Controladores;
import com.example.FinanzApp.DTOS.LoginRequest;
import com.example.FinanzApp.Servicios.ServicioLogin;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Login", description = "Encargado de la autenticación y autorización")
@RequestMapping("/Finanzapp")

public class ControladorLogin {

    @Autowired
    ServicioLogin servicioLogin;

    @PostMapping("/login")
    public ResponseEntity<?> AutenticarUsuario(@RequestBody LoginRequest loginRequest) {

        Map<String, Object> response = servicioLogin.AutenticarUsuario(loginRequest);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return  ResponseEntity.ok(response);
        }
    }

}
