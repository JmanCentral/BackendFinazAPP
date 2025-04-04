package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Deposito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioDeposito extends JpaRepository<Deposito, Long> {

    @Query("SELECT d FROM Deposito d WHERE d.alcancia.idAlcancia = :idAlcancia")
    List<Deposito> findByAlcancia(@Param("idAlcancia") Long idAlcancia);

    @Query("SELECT SUM(i.monto) " +
            "FROM Deposito i " +
            "WHERE i.usuario.id_usuario = :usuarioId " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM i.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)")
    Double getValorDepositosMes(@Param("usuarioId") Long usuarioId);


    @Modifying
    @Query("DELETE FROM Deposito d WHERE  d.alcancia.idAlcancia = :idAlcancia AND d.idDeposito = :idDeposito ")
    void deleteByDepositos(@Param("idDeposito") Long idDeposito, @Param("idAlcancia") Long idAlcancia);

}
