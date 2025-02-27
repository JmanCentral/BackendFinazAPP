package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Consejos;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RepositorioConsejos extends MongoRepository<Consejos, String> {

    @Aggregation(pipeline = {
            "{ $sample: { size: 10 } }"
    })
    List<Consejos> findRandomConsejos();
}
