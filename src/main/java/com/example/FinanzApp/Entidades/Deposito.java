package com.example.FinanzApp.Entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Deposito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeposito;
    @Column(nullable = false)
    private Double monto;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private String nombre_depositante;

    @ManyToOne
    @JoinColumn(name = "id_alcancia", nullable = false)
    private Alcancia alcancia;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

}

