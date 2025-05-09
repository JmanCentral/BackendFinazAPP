package com.example.finanzapp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastoDTO {

    private Long id_gasto;
    private String nombre_gasto;
    private String categoria;
    private LocalDate fecha;
    private Double valor;

}
