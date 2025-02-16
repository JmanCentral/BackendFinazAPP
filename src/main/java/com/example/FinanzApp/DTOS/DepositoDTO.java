package com.example.FinanzApp.DTOS;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DepositoDTO {

    private Long idDeposito;
    private Double monto;
    private String nombre_depositante;
    private LocalDate fecha;
}
