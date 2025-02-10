package com.example.FinanzApp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RecordatorioDTO {

    private Long id_recordatorio;
    private String nombre;
    private String estado;
    private LocalDate fecha;
    private Long dias_recordatorio;
    private Double valor;

}
