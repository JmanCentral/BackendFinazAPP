package com.example.finanzapp.Repositorios;

import com.example.finanzapp.Entidades.ERole;
import com.example.finanzapp.Entidades.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioRoles extends JpaRepository<Roles, Long>, JpaSpecificationExecutor<Roles> {

    Optional<Roles> findByName(ERole name);

}
