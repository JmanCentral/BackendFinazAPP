package com.example.FinanzApp.Entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consejos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsejo;

    @Column(unique = true, nullable = false)
    private String consejo;

    @OneToMany(mappedBy = "consejos", cascade = CascadeType.ALL ,  orphanRemoval = true )
    private List<Calificacion> calificacion;

}