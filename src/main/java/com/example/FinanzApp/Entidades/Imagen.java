package com.example.FinanzApp.Entidades;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImagen;

    @Column(nullable = false)
    private String url; // Ruta donde se almacena la imagen

    @OneToOne
    @JoinColumn(name = "id_alcancia", nullable = false, unique = true)
    private Alcancia alcancia;
}
