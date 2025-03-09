package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioCalificacion extends JpaRepository<Calificacion, String> {

    @Query("SELECT d FROM Calificacion d WHERE d.consejos.idConsejo= :idConsejo")
    List<Calificacion> findByConsejos(@Param("idConsejo") Long idConsejo);

}
