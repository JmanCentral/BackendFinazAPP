package com.example.finanzapp.Excepciones.Usuario;

public class EmailYaRegistradoException extends RuntimeException {
    public EmailYaRegistradoException(String message) {
        super(message);
    }
}
