package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepositorioAlerta extends JpaRepository<Alerta, Long>, JpaSpecificationExecutor<Alerta> {

    @Query("SELECT k FROM Alerta k WHERE k.usuario.id_usuario = :usuarioId")
    List<Alerta> getAlertasPorUsuario(@Param("usuarioId") Long usuarioId);

    @Query("SELECT k FROM Alerta k WHERE k.usuario.id_usuario = :usuarioId AND EXTRACT(YEAR FROM k.fecha) = EXTRACT(YEAR FROM CURRENT_DATE)")
    List<Alerta>  getAlertasDeEsteAno(@Param("usuarioId") Long usuarioId);

    @Query("SELECT k FROM Alerta k WHERE k.usuario.id_usuario = :usuarioId AND EXTRACT(YEAR FROM k.fecha) = EXTRACT(YEAR FROM CURRENT_DATE)")
    List<Alerta> getAlertasDeEsteMes(@Param("usuarioId") Long usuarioId);

    Optional<Alerta> findById(Long id_alerta);

    void deleteById(Long id_alerta);
    @Query("SELECT k FROM Alerta k WHERE k.usuario.id_usuario = :usuarioId AND EXTRACT(YEAR FROM k.fecha) = EXTRACT(YEAR FROM CURRENT_DATE)")
    List<Alerta> getValorTotalAlertasDeEsteMes(@Param("usuarioId") Long usuarioId);



}