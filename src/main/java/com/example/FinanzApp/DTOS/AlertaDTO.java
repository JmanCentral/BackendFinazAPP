package com.example.FinanzApp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertaDTO {

    private Long id_alerta;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private Double valor;

}