package com.example.FinanzApp.Repositorios;

import com.example.FinanzApp.Entidades.Consejos;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioPregunta extends MongoRepository<Consejos, String> {


}
