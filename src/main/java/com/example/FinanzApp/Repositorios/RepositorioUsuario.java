package com.example.FinanzApp.Repositorios;


import com.example.FinanzApp.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findById(Long id);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.roles WHERE u.username = :nombreUsuario")
    Optional<Usuario> findByUsernameConRoles(@Param("nombreUsuario") String nombreUsuario);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findAll();;


}
