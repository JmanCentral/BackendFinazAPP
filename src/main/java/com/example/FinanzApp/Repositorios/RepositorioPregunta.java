package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Consejos;
import com.example.FinanzApp.Entidades.Pregunta;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RepositorioPregunta extends MongoRepository<Pregunta, String> {

    @Aggregation(pipeline = {
            "{ $sample: { size: 10 } }"
    })
    List<Pregunta> findRandomPreguntas();

}
