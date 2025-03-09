package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCalificacion extends JpaRepository<Calificacion, String> {
}
