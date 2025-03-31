package com.example.finanzApp.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id_ingreso;
    @Column(nullable = false)
    private String nombre_ingreso;
    @Column(nullable = false)
    private Double valor;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private String tipo_ingreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

}
