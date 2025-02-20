package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Deposito;
import com.example.FinanzApp.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioDeposito extends JpaRepository<Deposito, Long> {

    @Query("SELECT d FROM Deposito d WHERE d.alcancia.idAlcancia = :idAlcancia")
    List<Deposito> findByAlcancia(@Param("idAlcancia") Long idAlcancia);

    @Query("SELECT DISTINCT d.usuario.token FROM Deposito d " +
            "WHERE d.alcancia.idAlcancia = :idAlcancia AND d.usuario.token IS NOT NULL")
    List<String> findTokensByAlcancia(@Param("idAlcancia") Long idAlcancia);




}
