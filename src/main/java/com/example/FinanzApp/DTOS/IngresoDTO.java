package com.example.FinanzApp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngresoDTO {

    private Long id_ingreso;
    private String nombre_ingreso;
    private Double valor;
    private Date fecha;
    private String tipo_ingreso;
    private Long id_usuario;

}
