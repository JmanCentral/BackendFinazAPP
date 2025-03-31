package com.example.finanzApp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngresoDTO {

    private Long id_ingreso;
    private String nombre_ingreso;
    private Double valor;
    private LocalDate fecha;
    private String tipo_ingreso;

}
