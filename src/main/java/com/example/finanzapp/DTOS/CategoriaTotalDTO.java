package com.example.finanzapp.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaTotalDTO {

    private String categoria;
    private Double totalvalor;
}
