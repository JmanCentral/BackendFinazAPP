package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Alcancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioAlcancia extends JpaRepository<Alcancia, Long> {

    List<Alcancia> findByCodigo(String codigo);

    @Query("SELECT j FROM Alcancia j WHERE j.usuario.id_usuario = :userId")
    List<Alcancia> findAlcanciasByUserId(@Param("userId") Long userId);


    void deleteById(Long idAlcancia);

    Optional<Alcancia> findById(Long id_gasto);


}
