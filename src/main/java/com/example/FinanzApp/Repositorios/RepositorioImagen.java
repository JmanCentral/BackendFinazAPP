package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Alcancia;
import com.example.FinanzApp.Entidades.Deposito;
import com.example.FinanzApp.Entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RepositorioImagen extends JpaRepository<Imagen, Long>{

    Optional<Imagen> findByAlcancia(Alcancia alcancia);
}

