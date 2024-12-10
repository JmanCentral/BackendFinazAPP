package com.example.FinanzApp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertaDTO {

    private Long id_alerta;
    private String nombre;
    private String descripcion;
    private String fecha;
    private Double valor;

}
