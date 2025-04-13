package com.example.finanzapp.Excepciones.Usuario;

public class UsernameInvalidoException  extends RuntimeException {
    public UsernameInvalidoException(String message) {
        super(message);
    }
}
