package com.example.FinanzApp.Repositorios;


import com.example.FinanzApp.Entidades.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioRecordatorio  extends JpaRepository<Recordatorio, Long>, JpaSpecificationExecutor<Recordatorio>  {

    Optional<Recordatorio> findById(Long aLong);

    @Query("SELECT r FROM Recordatorio r WHERE r.usuario.id_usuario = :usuarioId")
    List<Recordatorio> findByUsuarioId(Long usuarioId);

    @Query("SELECT r from Recordatorio r WHERE r.nombre = :nombre")
    List<Recordatorio> findByNombre(String nombre);

    void deleteById(Long id_recordatorio);

    @Modifying
    @Query("DELETE FROM Recordatorio r WHERE r.usuario.id_usuario = :usuarioId")
    void deleteByUsuario(@Param("usuarioId") Long usuarioId);


}
