package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.ERole;
import com.example.FinanzApp.Entidades.Roles;
import com.example.FinanzApp.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface RepositorioRoles extends JpaRepository<Roles, Long>, JpaSpecificationExecutor<Roles> {

    Optional<Roles> findByName(ERole name);

}
