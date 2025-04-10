package com.example.finanzapp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaCorreo {
    private String message;
    private boolean success;
}
