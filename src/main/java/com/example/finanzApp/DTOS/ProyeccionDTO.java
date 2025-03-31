package com.example.finanzApp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ProyeccionDTO {
    private String descripcion;
    private Integer cantidad;
    private Double total;

}
