package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RepositorioAlerta extends JpaRepository<Alerta, Long>, JpaSpecificationExecutor<Alerta> {


}
