package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Consejos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioConsejos extends JpaRepository<Consejos, Long> {

    List<Consejos> findAll();
}
