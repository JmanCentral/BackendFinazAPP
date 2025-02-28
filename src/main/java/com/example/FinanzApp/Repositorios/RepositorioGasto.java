package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.CategoriaTotal;
import com.example.FinanzApp.Entidades.GastoProjection;
import com.example.FinanzApp.Entidades.Gasto;
import com.example.FinanzApp.Entidades.Usuario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RepositorioGasto extends JpaRepository<Gasto, Long>, JpaSpecificationExecutor<Gasto> {

    @Query("SELECT " +
            "COALESCE(SUM(i.valor), 0) - COALESCE((SELECT SUM(g.valor) FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId), 0) " +
            " - COALESCE((SELECT SUM(d.monto) FROM Deposito d WHERE d.usuario.id_usuario = :usuarioId), 0) " +
            "FROM Ingreso i WHERE i.usuario.id_usuario = :usuarioId")
    Double getDisponible(@Param("usuarioId") Long usuarioId);

    @Query("SELECT " +
            "COALESCE(SUM(i.valor), 0) - COALESCE((SELECT SUM(g.valor) FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId AND g.fecha BETWEEN :fechaInf AND :fechaSup), 0) " +
            "FROM Ingreso i WHERE i.usuario.id_usuario = :usuarioId AND i.fecha BETWEEN :fechaInf AND :fechaSup")
    Double getDisponiblePorFechas(
            @Param("usuarioId") Long usuarioId,
            @Param("fechaInf") LocalDate fechaInf,
            @Param("fechaSup") LocalDate fechaSup
    );

    @Query("SELECT g FROM Gasto g " +
            "WHERE g.usuario.id_usuario = :usuarioId " +
            "AND g.categoria = :categoria " +
            "AND EXTRACT(YEAR FROM g.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM g.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)")
    List<Gasto> getGastosMesCategoria(
            @Param("usuarioId") Long usuarioId,
            @Param("categoria") String categoria
    );

    @Query("SELECT g FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId")
    List<Gasto> findGastosByUsuarioId(@Param("usuarioId") Long usuarioId);


    List<Gasto> findByUsuarioAndFechaBetween(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin);


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
    Gasto getValorMasAlto(@Param("usuarioId") Long usuarioId);

    @Query("SELECT g FROM Gasto g WHERE g.usuario.id_usuario= :usuarioId ORDER BY g.valor  ASC LIMIT 1")
    Gasto getValorMasBajo(@Param("usuarioId") Long usuarioId);

    @Query("SELECT AVG(g.valor) FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId AND EXTRACT(YEAR FROM g.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) \n" +
            "AND EXTRACT(MONTH FROM g.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)  ")
    Double getPromedioGastosMes(@Param("usuarioId") Long usuarioId);

    @Query("SELECT g.nombre_gasto FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId " +
            "GROUP BY g.nombre_gasto ORDER BY COUNT (g.nombre_gasto) DESC LIMIT 1")
    String getDescripcionRecurrente(@Param("usuarioId") Long usuarioId);

    @Query("SELECT (COALESCE(SUM(g.valor), 0) / COALESCE(SUM(i.valor), 1)) * 100 " +
            "FROM Gasto g, Ingreso i " +
            "WHERE g.usuario.id_usuario = :usuarioId AND i.usuario.id_usuario = :usuarioId")
    Double getPorcentajeGastosSobreIngresos(@Param("usuarioId") Long usuarioId);

    @Query("SELECT g.categoria, SUM(g.valor) as totalValor " +
            "FROM Gasto g " +
            "WHERE g.usuario.id_usuario = :usuarioId " +
            "GROUP BY g.categoria " +
            "ORDER BY totalValor DESC " +
            "LIMIT 1")
    CategoriaTotal getCategoriaConMasGastos(@Param("usuarioId") Long usuarioId);


    @Query("SELECT g FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId")
    List<Gasto> findByUsuario(@Param("usuarioId") Long usuarioId);

    @Query("SELECT g.nombre_gasto AS descripcion, COUNT(g) AS cantidad, SUM(g.valor) AS total " +
            "FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId AND g.valor < 100000000 " +
            "GROUP BY g.nombre_gasto ORDER BY total DESC")
    List<GastoProjection> findGastosFrecuentes(@Param("usuarioId") Long usuarioId);


    @Query("SELECT SUM(g.valor) / COUNT(DISTINCT g.fecha) AS gastoPromedioDiario FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId ")
    Double getGastoPromedioDiarioTotal(@Param("usuarioId") Long usuarioId);


    @Query("SELECT g FROM Gasto g WHERE g.nombre_gasto = :nombreGasto AND g.categoria = :categoria AND g.usuario.id_usuario = :usuarioId")
    List<Gasto> findByNombreGastoAndCategoriaAndUsuarioId(@Param("nombreGasto") String nombreGasto,
                                                          @Param("categoria") String categoria,
                                                          @Param("usuarioId") Long usuarioId);


    @Modifying
    @Query("DELETE FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId AND g.categoria = :categoria")
    void deleteByUsuarioIdAndCategoria(@Param("usuarioId") Long usuarioId,
                                                @Param("categoria") String categoria);



}
