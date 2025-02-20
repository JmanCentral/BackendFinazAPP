package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Deposito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioDeposito extends JpaRepository<Deposito, Long> {

    @Query("SELECT d FROM Deposito d WHERE d.alcancia.idAlcancia = :idAlcancia")
    List<Deposito> findByAlcancia(@Param("idAlcancia") Long idAlcancia);

    @Query("SELECT SUM(i.monto) " +
            "FROM Deposito i " +
            "WHERE i.usuario.id_usuario = :usuarioId " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM i.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)")
    Double getValorDepositosMes(@Param("usuarioId") Long usuarioId);




}
