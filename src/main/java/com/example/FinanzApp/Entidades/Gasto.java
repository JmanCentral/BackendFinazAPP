package com.example.FinanzApp.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gasto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id_gasto;
    @Column(nullable = false)
    private String nombre_gasto;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private Date fecha;
    @Column(nullable = false)
    private Double valor;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;


}
