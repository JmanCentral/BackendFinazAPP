package com.example.finanzapp.Excepciones.Usuario;


public class CorreoInvalidoException extends RuntimeException {
    public CorreoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
