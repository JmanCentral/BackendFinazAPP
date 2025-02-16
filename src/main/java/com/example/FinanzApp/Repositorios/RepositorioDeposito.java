package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Deposito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioDeposito extends JpaRepository<Deposito, Long> {
}
