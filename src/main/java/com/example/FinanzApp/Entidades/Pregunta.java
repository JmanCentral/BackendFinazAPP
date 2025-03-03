package com.example.FinanzApp.Entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "preguntas")
public class Pregunta {

    private Long id_pregunta;
    private String pregunta;
    private String op1;
    private String op2;
    private String op3;
    private String op4;
    private String respuesta;
}
