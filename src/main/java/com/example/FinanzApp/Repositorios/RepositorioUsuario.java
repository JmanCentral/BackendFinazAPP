package com.example.FinanzApp.Repositorios;


import com.example.FinanzApp.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByUsernameAndContrasena(String nombreUsuario , String contrasena);

    Optional<Usuario> findByUsername(String nombreUsuario);




}
