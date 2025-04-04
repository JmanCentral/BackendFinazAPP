package com.example.FinanzApp.Components;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class CodigoGenerador {

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LONGITUD_CODIGO = 10;
    private final SecureRandom random = new SecureRandom();

    public String generarCodigo() {
        StringBuilder codigo = new StringBuilder(LONGITUD_CODIGO);
        for (int i = 0; i < LONGITUD_CODIGO; i++) {
            codigo.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
        }
        return codigo.toString();
    }

}
