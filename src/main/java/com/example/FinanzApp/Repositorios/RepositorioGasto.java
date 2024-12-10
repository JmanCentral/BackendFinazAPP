package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Gasto;
import com.example.FinanzApp.Entidades.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RepositorioGasto extends JpaRepository<Gasto, Long>, JpaSpecificationExecutor<Gasto> {


}
