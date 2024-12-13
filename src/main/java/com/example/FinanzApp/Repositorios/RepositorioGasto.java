package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RepositorioGasto extends JpaRepository<Gasto, Long>, JpaSpecificationExecutor<Gasto> {

    @Query("SELECT " +
            "COALESCE(SUM(i.valor), 0) - COALESCE((SELECT SUM(g.valor) FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId), 0) " +
            "FROM Ingreso i WHERE i.usuario.id_usuario = :usuarioId")
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


    @Query("SELECT SUM(g.valor) FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId AND EXTRACT(YEAR FROM g.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM g.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)")
    Double getValorGastosMes(@Param("usuarioId") Long usuarioId);

    void deleteById(Long id_gasto);

    Optional<Gasto> findById(Long id_gasto);

    @Query("SELECT g FROM Gasto g WHERE g.usuario.id_usuario = :idUsuario AND g.fecha BETWEEN :fechaInf AND :fechaSup")
    List<Gasto> getGastosPorFechas(@Param("idUsuario") Long idUsuario,
                                             @Param("fechaInf") LocalDate fechaInf,
                                             @Param("fechaSup") LocalDate fechaSup);


    @Query("SELECT g FROM Gasto g WHERE g.usuario.id_usuario= :usuarioId ORDER BY g.valor ASC")
    List<Gasto> findByUsuarioIdOrderByValorAsc(@Param("usuarioId") Long usuarioId);

    @Query("SELECT g FROM Gasto g WHERE g.usuario.id_usuario= :usuarioId ORDER BY g.valor DESC")
    List<Gasto> findByUsuarioIdOrderByValorDesc(@Param("usuarioId") Long usuarioId);

    @Query("SELECT g FROM Gasto g WHERE g.usuario.id_usuario= :usuarioId ORDER BY g.valor  DESC LIMIT 1")
    List<Gasto> getValorMasAlto(@Param("usuarioId") Long usuarioId);

    @Query("SELECT g FROM Gasto g WHERE g.usuario.id_usuario= :usuarioId ORDER BY g.valor  ASC LIMIT 1")
    List<Gasto> getValorMasBajo(@Param("usuarioId") Long usuarioId);

    @Query ("SELECT AVG(g.valor) FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId AND EXTRACT(YEAR FROM g.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) \n" +
            "AND EXTRACT(MONTH FROM g.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)  ")
    Double getPromedioGastosMes(@Param("usuarioId") Long usuarioId);



}
