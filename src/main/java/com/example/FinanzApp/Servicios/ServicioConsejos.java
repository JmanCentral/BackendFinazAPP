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


        consejos.add(new Consejos(null, "1. Define metas financieras claras a corto, mediano y largo plazo."));
        consejos.add(new Consejos(null, "2. Registra todos tus ingresos y gastos para tener un control total de tu economía."));
        consejos.add(new Consejos(null, "3. Elabora un presupuesto mensual realista y cúmplelo rigurosamente."));
        consejos.add(new Consejos(null, "4. Diferencia entre necesidades y lujos para priorizar tus gastos esenciales."));
        consejos.add(new Consejos(null, "5. Crea un fondo de emergencia que cubra entre 3 y 6 meses de gastos."));
        consejos.add(new Consejos(null, "6. Evita endeudarte con créditos de alto interés y condiciones desfavorables."));
        consejos.add(new Consejos(null, "7. Busca asesoría financiera profesional para optimizar tus inversiones."));
        consejos.add(new Consejos(null, "8. Invierte en tu educación financiera mediante cursos y talleres especializados."));
        consejos.add(new Consejos(null, "9. Utiliza herramientas digitales para el seguimiento y control de tus finanzas."));
        consejos.add(new Consejos(null, "10. Revisa y ajusta tu plan financiero de manera periódica."));
        consejos.add(new Consejos(null, "11. Diversifica tus inversiones para minimizar riesgos y maximizar retornos."));
        consejos.add(new Consejos(null, "12. Infórmate sobre las oportunidades de inversión y ahorro en Colombia."));
        consejos.add(new Consejos(null, "13. Utiliza aplicaciones móviles para registrar y monitorear tus gastos diarios."));
        consejos.add(new Consejos(null, "14. Establece metas de ahorro para proyectos importantes y emergencias imprevistas."));
        consejos.add(new Consejos(null, "15. Planifica tus compras grandes y evita decisiones impulsivas de gasto."));
        consejos.add(new Consejos(null, "16. Aprende sobre la inflación y su impacto en el poder adquisitivo de tu dinero."));
        consejos.add(new Consejos(null, "17. Aprovecha los beneficios y descuentos que ofrecen bancos y entidades locales."));
        consejos.add(new Consejos(null, "18. Consulta fuentes oficiales y entidades financieras para obtener información confiable."));
        consejos.add(new Consejos(null, "19. Mantén un historial crediticio saludable revisando periódicamente tu reporte de crédito."));
        consejos.add(new Consejos(null, "20. Fomenta una cultura del ahorro e inversión en tu entorno familiar y social."));


        return consejos;
    }

    public List<Consejos> obtenerConsejosAleatorios() {
        return repositorioConsejos.findRandomConsejos();
    }


}

