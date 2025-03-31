package com.example.finanzApp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalificacionDTO {

    private Long idCalificacion;
    private int me_gusta;
    private int no_me_gusta;
    private Long id_usuario;
    private Long idConsejo;
}
