package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.Entidades.Consejos;
import com.example.FinanzApp.Repositorios.RepositorioConsejos;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ServicioConsejos {


    private RepositorioConsejos repositorioConsejos;

    public void insertAllConsejos() {
        List<Consejos> consejos = obtenerConsejos();
        repositorioConsejos.saveAll(consejos);
    }

    private List<Consejos> obtenerConsejos() {
        List<Consejos> consejos = new ArrayList<>();


        consejos.add(new Consejos(null, "Define metas financieras claras a corto, mediano y largo plazo."));
        consejos.add(new Consejos(null, "Registra todos tus ingresos y gastos para tener un control total de tu economía."));
        consejos.add(new Consejos(null, "Elabora un presupuesto mensual realista y cúmplelo rigurosamente."));
        consejos.add(new Consejos(null, "Diferencia entre necesidades y lujos para priorizar tus gastos esenciales."));
        consejos.add(new Consejos(null, "Crea un fondo de emergencia que cubra entre 3 y 6 meses de gastos."));
        consejos.add(new Consejos(null, "Evita endeudarte con créditos de alto interés y condiciones desfavorables."));
        consejos.add(new Consejos(null, "Busca asesoría financiera profesional para optimizar tus inversiones."));
        consejos.add(new Consejos(null, "Invierte en tu educación financiera mediante cursos y talleres especializados."));
        consejos.add(new Consejos(null, "Utiliza herramientas digitales para el seguimiento y control de tus finanzas."));
        consejos.add(new Consejos(null, "Revisa y ajusta tu plan financiero de manera periódica."));
        consejos.add(new Consejos(null, "Diversifica tus inversiones para minimizar riesgos y maximizar retornos."));
        consejos.add(new Consejos(null, "Infórmate sobre las oportunidades de inversión y ahorro en Colombia."));
        consejos.add(new Consejos(null, "Utiliza aplicaciones móviles para registrar y monitorear tus gastos diarios."));
        consejos.add(new Consejos(null, "Establece metas de ahorro para proyectos importantes y emergencias imprevistas."));
        consejos.add(new Consejos(null, "Planifica tus compras grandes y evita decisiones impulsivas de gasto."));
        consejos.add(new Consejos(null, "Aprende sobre la inflación y su impacto en el poder adquisitivo de tu dinero."));
        consejos.add(new Consejos(null, "Aprovecha los beneficios y descuentos que ofrecen bancos y entidades locales."));
        consejos.add(new Consejos(null, "Consulta fuentes oficiales y entidades financieras para obtener información confiable."));
        consejos.add(new Consejos(null, "Mantén un historial crediticio saludable revisando periódicamente tu reporte de crédito."));
        consejos.add(new Consejos(null, "Fomenta una cultura del ahorro e inversión en tu entorno familiar y social."));


        return consejos;
    }

    public List<Consejos> obtenerConsejosAleatorios() {
        return repositorioConsejos.findRandomConsejos();
    }


}

