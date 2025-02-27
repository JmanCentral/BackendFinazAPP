package com.example.FinanzApp.Servicios;
import com.example.FinanzApp.Entidades.Pregunta;
import com.example.FinanzApp.Repositorios.RepositorioPregunta;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Service
public class ServicioPregunta {

    private final RepositorioPregunta repositorioPregunta;

    public void insertAllPreguntas() {
        List<Pregunta> preguntas = obtenerPreguntas();
        repositorioPregunta.saveAll(preguntas);
    }

    private List<Pregunta> obtenerPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();

        preguntas.add(new Pregunta(null, "¿Cuál es el primer paso para mejorar tus finanzas personales?",
                "A) Ahorrar dinero sin un plan",
                "B) Definir metas financieras",
                "C) No gastar en entretenimiento",
                "D) Invertir en criptomonedas",
                "B"));

        preguntas.add(new Pregunta(null, "¿Por qué es importante llevar un registro de ingresos y gastos?",
                "A) Para saber en qué gastas tu dinero",
                "B) Para poder endeudarte más fácilmente",
                "C) Para ocultar tus gastos",
                "D) No tiene importancia",
                "A"));

        preguntas.add(new Pregunta(null, "¿Qué porcentaje de tus ingresos deberías ahorrar idealmente cada mes?",
                "A) 5%",
                "B) 10%",
                "C) 20%",
                "D) 50%",
                "C"));

        preguntas.add(new Pregunta(null, "¿Qué es un fondo de emergencia?",
                "A) Un préstamo para casos urgentes",
                "B) Un ahorro que cubra de 3 a 6 meses de gastos",
                "C) Un seguro de vida",
                "D) Un plan de inversión de alto riesgo",
                "B"));

        preguntas.add(new Pregunta(null, "¿Cuál de los siguientes es un gasto fijo?",
                "A) Pago de alquiler",
                "B) Comida en un restaurante",
                "C) Compra de ropa",
                "D) Viaje de vacaciones",
                "A"));

        preguntas.add(new Pregunta(null, "¿Qué es el interés compuesto?",
                "A) El interés que se paga sobre los intereses generados previamente",
                "B) El interés fijo que pagas en un crédito",
                "C) Un impuesto adicional a los intereses",
                "D) Un préstamo sin intereses",
                "A"));

        preguntas.add(new Pregunta(null, "¿Cuál es una ventaja de pagar con tarjeta de crédito de manera responsable?",
                "A) Construir un buen historial crediticio",
                "B) Poder gastar sin límite",
                "C) Evitar pagar deudas",
                "D) No tener que pagar nunca",
                "A"));

        preguntas.add(new Pregunta(null, "¿Cuál de estas opciones es una inversión de bajo riesgo?",
                "A) Acciones en empresas nuevas",
                "B) Fondos de inversión indexados",
                "C) Criptomonedas",
                "D) Juegos de apuestas",
                "B"));

        preguntas.add(new Pregunta(null, "¿Cómo se puede evitar caer en deudas innecesarias?",
                "A) Solo usar efectivo",
                "B) Hacer un presupuesto y gastar con control",
                "C) Pedir préstamos con frecuencia",
                "D) Usar varias tarjetas de crédito",
                "B"));

        preguntas.add(new Pregunta(null, "¿Por qué es importante diversificar las inversiones?",
                "A) Para aumentar los riesgos",
                "B) Para minimizar las pérdidas y maximizar ganancias",
                "C) Para apostar todo en un solo activo",
                "D) No es necesario diversificar",
                "B"));

        preguntas.add(new Pregunta(null, "¿Cuál es una forma efectiva de reducir gastos innecesarios?",
                "A) Evitar hacer un presupuesto",
                "B) Identificar gastos hormiga y eliminarlos",
                "C) Comprar por impulso",
                "D) Usar siempre la tarjeta de crédito",
                "B"));

        preguntas.add(new Pregunta(null, "¿Qué es el historial crediticio?",
                "A) Un registro de los ingresos de una persona",
                "B) Un informe de cómo una persona ha manejado sus deudas",
                "C) Un documento de identidad financiero",
                "D) Una cuenta bancaria",
                "B"));

        preguntas.add(new Pregunta(null, "¿Cuál de los siguientes puede mejorar tu historial crediticio?",
                "A) Pagar tus deudas a tiempo",
                "B) No usar nunca tarjetas de crédito",
                "C) Evadir el pago de impuestos",
                "D) Tener muchas tarjetas sin usarlas",
                "A"));

        preguntas.add(new Pregunta(null, "¿Qué beneficio tiene establecer metas financieras?",
                "A) Controlar mejor el dinero y tomar mejores decisiones",
                "B) Gastar sin límites",
                "C) No ahorrar dinero",
                "D) Evitar pagar impuestos",
                "A"));

        preguntas.add(new Pregunta(null, "¿Cómo puedes evitar caer en estafas financieras?",
                "A) Investigando antes de invertir",
                "B) Creyendo en promesas de dinero fácil",
                "C) Compartiendo tus datos bancarios con desconocidos",
                "D) Aceptando cualquier oferta sin leer los términos",
                "A"));

        preguntas.add(new Pregunta(null, "¿Qué es una tasa de interés?",
                "A) Un porcentaje que pagas o recibes por un préstamo o inversión",
                "B) Un impuesto sobre las compras",
                "C) Un descuento bancario",
                "D) Una tarifa por abrir una cuenta",
                "A"));

        preguntas.add(new Pregunta(null, "¿Por qué es recomendable tener una cuenta de ahorro?",
                "A) Para ganar intereses sobre tu dinero",
                "B) Para guardar dinero sin rendimientos",
                "C) Para evitar el uso del efectivo",
                "D) No tiene ningún beneficio",
                "A"));

        preguntas.add(new Pregunta(null, "¿Qué tipo de seguro es esencial para proteger tus finanzas?",
                "A) Seguro de auto si tienes vehículo",
                "B) Seguro de vida",
                "C) Seguro de salud",
                "D) Todas las anteriores",
                "D"));

        preguntas.add(new Pregunta(null, "¿Cuál es un hábito clave para lograr estabilidad financiera?",
                "A) Evitar planificar los gastos",
                "B) Ahorrar regularmente y gastar con control",
                "C) Usar todo el sueldo sin restricciones",
                "D) Endeudarse para comprar bienes innecesarios",
                "B"));

        return preguntas;
    }


    public List<Pregunta> obtenerpreguntasAleatorios() {
        return repositorioPregunta.findRandomPreguntas();
    }

}
