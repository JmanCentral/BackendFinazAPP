package com.example.finanzApp.DTOS;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AlcanciaDTO {

    private Long idAlcancia;
    private String nombre_alcancia;
    private Double meta;
    private Double saldoActual = 0.0;
    private String codigo;
    private LocalDate fechaCreacion;
}
