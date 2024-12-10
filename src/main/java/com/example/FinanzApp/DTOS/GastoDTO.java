package com.example.FinanzApp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastoDTO {

    private Long id_gasto;
    private String nombre_gasto;
    private String categoria;
    private String fecha;
    private Double valor;

}
