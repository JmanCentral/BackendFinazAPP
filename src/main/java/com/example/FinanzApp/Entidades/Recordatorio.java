package com.example.FinanzApp.Entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Recordatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id_recordatorio;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String estado;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private Long dias_recordatorio;
    @Column(nullable = false)
    private Double valor;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

}
