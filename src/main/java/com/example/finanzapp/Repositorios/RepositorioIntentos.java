package com.example.finanzapp.Repositorios;

import com.example.finanzapp.Entidades.Intentos;
import com.example.finanzapp.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioIntentos extends JpaRepository<Intentos, Long> {

    Optional<Intentos> findByUsuario(Usuario usuario);
}
