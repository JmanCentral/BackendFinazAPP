package com.example.FinanzApp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TipsDTO {
    private String titulo;
    private String contenido;
}

