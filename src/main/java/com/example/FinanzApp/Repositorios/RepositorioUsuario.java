package com.example.FinanzApp.Repositorios;


import com.example.FinanzApp.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByUsername(String nombreUsuario);

    Optional<Usuario> findByEmail(String email);



}
