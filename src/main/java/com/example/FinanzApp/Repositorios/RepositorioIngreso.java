package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Ingreso;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RepositorioIngreso  extends JpaRepository<Ingreso, Long>, JpaSpecificationExecutor<Ingreso> {


    @Query("SELECT i FROM Ingreso i " +
            "WHERE i.usuario.id_usuario = :usuarioId " +
            "AND i.tipo_ingreso = 'casual' " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE)")
    List<Ingreso> verificacion(@Param("usuarioId") Long usuarioId);


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

    @Query("SELECT i " +
            "FROM Ingreso i " +
            "WHERE i.usuario.id_usuario = :usuarioId " +
            "AND i.tipo_ingreso = 'mensual' " +
            "AND YEAR(i.fecha) = :anio " +
            "AND MONTH(i.fecha) = :mes")
    List<Ingreso> getIngresosMensuales(@Param("usuarioId") Long usuarioId,
                                       @Param("anio") Integer anio,
                                       @Param("mes") Integer mes);


    @Modifying
    @Transactional
    @Query("UPDATE Ingreso SET nombre_ingreso = :nombre, fecha = :fecha, valor = :valor WHERE id_ingreso = :id")
    void modificarIngreso(@Param("nombre") String nombre,
                          @Param("fecha") Date fecha,
                          @Param("valor") Double valor,
                          @Param("id") Long id);

    void deleteById(Long id);

    @Query("SELECT SUM(i.valor) * 1.05 " +
            "FROM Ingreso i " +
            "WHERE i.usuario.id_usuario = :usuarioId " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM i.fecha) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND i.tipo_ingreso = 'mensual'")
    Double calcularTotalMensual(@Param("usuarioId") Long usuarioId);


}
