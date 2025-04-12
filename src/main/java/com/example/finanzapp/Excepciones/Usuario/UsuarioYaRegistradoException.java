package com.example.finanzapp.Excepciones.Usuario;

public class UsuarioYaRegistradoException extends RuntimeException {
    public UsuarioYaRegistradoException(String message) {
        super(message);
    }
}
