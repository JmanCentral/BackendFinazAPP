package com.example.FinanzApp.Entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Alcancia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlcancia;

    @Column(nullable = false)
    private String nombre_alcancia;

    @Column(nullable = false)
    private Double meta;

    @Column(nullable = false)
    private Double saldoActual;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    @Column(nullable = false, unique = true, length = 10)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}

