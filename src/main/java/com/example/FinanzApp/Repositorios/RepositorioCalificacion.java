package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioCalificacion extends JpaRepository<Calificacion, String> {

    List<Calificacion> findAll();

    @Query("SELECT COUNT(c) FROM Calificacion c WHERE c.usuario.id_usuario = :idUsuario AND c.consejos.idConsejo = :idConsejo AND c.me_gusta = 1")
    int countLikesByUsuarioAndConsejo(@Param("idUsuario") Long idUsuario, @Param("idConsejo") Long idConsejo);

    @Query("SELECT COUNT(c) FROM Calificacion c WHERE c.usuario.id_usuario = :idUsuario AND c.consejos.idConsejo= :idConsejo AND c.no_me_gusta = 1")
    int countDislikesByUsuarioAndConsejo(@Param("idUsuario") Long idUsuario, @Param("idConsejo") Long idConsejo);

}
