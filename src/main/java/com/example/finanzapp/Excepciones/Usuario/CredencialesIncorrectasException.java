package com.example.finanzapp.Excepciones.Usuario;

public class CredencialesIncorrectasException extends RuntimeException {
    public CredencialesIncorrectasException(String message) {
        super(message);
    }
}
