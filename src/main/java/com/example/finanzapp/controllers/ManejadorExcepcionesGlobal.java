package com.example.finanzapp.controllers;

import com.example.finanzapp.Excepciones.Usuario.CorreoInvalidoException;
import com.example.finanzapp.Excepciones.Usuario.EmailYaRegistradoException;
import com.example.finanzapp.Excepciones.Usuario.RolNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ManejadorExcepcionesGlobal {

    @ExceptionHandler(CorreoInvalidoException.class)
    public ResponseEntity<?> manejarCorreoInvalido(CorreoInvalidoException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(EmailYaRegistradoException.class)
    public ResponseEntity<?> manejarEmailYaExiste(EmailYaRegistradoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RolNoEncontradoException.class)
    public ResponseEntity<?> manejarRolNoEncontrado(RolNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    // Manejo genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejarGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
    }
}

