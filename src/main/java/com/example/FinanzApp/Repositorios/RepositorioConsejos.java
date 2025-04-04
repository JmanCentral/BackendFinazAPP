package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Consejos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioConsejos extends JpaRepository<Consejos, Long> {

    List<Consejos> findAll();
}
