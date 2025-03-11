package com.example.FinanzApp.Entidades;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idCalificacion;

    @Column(nullable = false)
    private int me_gusta;

    @Column(nullable = false)
    private int no_me_gusta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idConsejo", nullable = false)
    private Consejos consejos;

}