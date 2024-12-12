package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioGasto extends JpaRepository<Gasto, Long>, JpaSpecificationExecutor<Gasto> {

    @Query("SELECT COALESCE(SUM(i.valor), 0) - COALESCE(SUM(g.valor), 0) " +
            "FROM Ingreso i " +
            "LEFT JOIN Gasto g ON i.usuario.id_usuario = g.usuario.id_usuario " +
            "WHERE i.usuario.id_usuario = :usuarioId")
    Double getDisponible(@Param("usuarioId") Long usuarioId);

    @Query("SELECT g FROM Gasto g " +
            "WHERE g.usuario.id_usuario = :usuarioId " +
            "AND g.categoria = :categoria " +
            "AND EXTRACT(YEAR FROM g.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM g.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)")

    List<Gasto> getGastosMesCategoria(
            @Param("usuarioId") Long usuarioId,
            @Param("categoria") String categoria
    );

    @Query("SELECT SUM(g.valor) " +
            "FROM Gasto g " +
            "WHERE g.usuario.id_usuario = :usuarioId " +
            "AND g.categoria = :categoria " +
            "AND EXTRACT(YEAR FROM g.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM g.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)")
    Double getValorGastosMesCategoria(@Param("usuarioId") Long usuarioId,
                                                @Param("categoria") String categoria);



}
