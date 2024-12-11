package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioIngreso  extends JpaRepository<Ingreso, Long>, JpaSpecificationExecutor<Ingreso> {

    @Query("SELECT i FROM Ingreso i WHERE i.usuario.id_usuario = :usuarioId " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM i.fecha) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND i.tipo_ingreso = 'mensual'")
    List<Ingreso> findIngresosMensualesByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT i FROM Ingreso i WHERE i.usuario.id_usuario = :usuarioId " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM i.fecha) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND i.tipo_ingreso = 'casual'")
    List<Ingreso> findIngresosCasualesDelMes(@Param("usuarioId") Long usuarioId);

    @Query("SELECT SUM(i.valor) FROM Ingreso i WHERE i.usuario.id_usuario = :usuarioId " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM i.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)")
    Double getIngTotalDeEsteMes(@Param("usuarioId") Long usuarioId);




}
