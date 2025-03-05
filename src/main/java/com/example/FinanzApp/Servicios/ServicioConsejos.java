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


        consejos.add(new Consejos(null, "Establecer objetivos financieros específicos te ayudará a tomar decisiones más acertadas sobre el manejo de tu dinero. Define metas a corto plazo (como ahorrar para un fondo de emergencia en seis meses), a mediano plazo (como pagar una deuda en dos años) y a largo plazo (como comprar una casa o prepararte para la jubilación). Usa estrategias como el método SMART (específicas, medibles, alcanzables, relevantes y con un tiempo determinado) para asegurarte de que tus metas sean realistas y alcanzables."));
        consejos.add(new Consejos(null, "Llevar un registro detallado de cada peso que entra y sale de tu bolsillo es fundamental para entender cómo manejas tu dinero. Puedes utilizar aplicaciones móviles, hojas de cálculo o incluso una libreta para anotar cada transacción. Clasifica tus gastos en categorías como vivienda, alimentación, transporte y entretenimiento para identificar oportunidades de ahorro y optimización."));
        consejos.add(new Consejos(null, "Un presupuesto es la herramienta clave para controlar tus finanzas. Calcula tus ingresos netos y distribúyelos en gastos esenciales, ahorros e inversiones. Es importante ser realista y asignar montos adecuados a cada categoría. Ajusta tu presupuesto según tus necesidades y monitorea constantemente tu progreso para asegurarte de que no te desvíes de tus objetivos."));
        consejos.add(new Consejos(null, "Una buena gestión financiera comienza con la capacidad de distinguir entre lo que realmente necesitas y lo que simplemente deseas. Las necesidades incluyen vivienda, alimentación y salud, mientras que los lujos pueden ser compras impulsivas, tecnología de última generación o cenas costosas. Aprende a posponer gastos innecesarios y prioriza lo que realmente impacta tu calidad de vida."));
        consejos.add(new Consejos(null, "Un fondo de emergencia te protegerá ante imprevistos como pérdida de empleo, emergencias médicas o reparaciones inesperadas. Lo ideal es ahorrar entre tres y seis meses de tus gastos mensuales básicos. Deposítalo en una cuenta de fácil acceso pero separada de tu cuenta principal para evitar la tentación de gastarlo en cosas innecesarias."));
        consejos.add(new Consejos(null, "Antes de solicitar un crédito, analiza las tasas de interés, plazos y condiciones. Las tarjetas de crédito y préstamos rápidos pueden parecer una solución inmediata, pero si no los administras bien, pueden convertirse en una carga financiera. Opta por financiamientos con tasas bajas y pagos manejables, y evita comprar a crédito cosas que no generen valor a futuro."));
        consejos.add(new Consejos(null, "Si tienes ingresos estables y deseas hacer crecer tu dinero, considera acudir a un asesor financiero. Un experto puede orientarte sobre las mejores opciones de inversión, la diversificación de tu portafolio y estrategias para minimizar riesgos. La educación financiera es clave para tomar decisiones informadas y maximizar el rendimiento de tus ahorros."));
        consejos.add(new Consejos(null, "La educación financiera te permite tomar mejores decisiones económicas y evitar errores comunes. Existen muchas plataformas en línea, libros y seminarios gratuitos o pagos que te pueden enseñar sobre ahorro, inversión, impuestos y planificación financiera. Dedica tiempo a aprender sobre estos temas y aplícalos en tu vida diaria."));
        consejos.add(new Consejos(null, "Hoy en día, hay muchas aplicaciones y plataformas que te ayudan a gestionar tu dinero de manera eficiente. Estas herramientas te permiten categorizar gastos, establecer presupuestos, recibir alertas de vencimientos y analizar tu comportamiento financiero. Algunas incluso ofrecen recomendaciones personalizadas para mejorar tu situación económica."));
        consejos.add(new Consejos(null, "La planificación financiera no es algo que se hace una sola vez. Debes revisar tu presupuesto y estrategias cada cierto tiempo para asegurarte de que sigues en el camino correcto. Si tu situación económica cambia, ajusta tu plan de ahorro, inversión y gastos para adaptarte a las nuevas circunstancias."));
        consejos.add(new Consejos(null, "No pongas todo tu dinero en una sola inversión. Distribuir tu capital en diferentes activos (acciones, bonos, fondos de inversión, bienes raíces, entre otros) ayuda a reducir riesgos y mejorar tus oportunidades de crecimiento. Investiga bien antes de invertir y mantente informado sobre las tendencias del mercado."));
        consejos.add(new Consejos(null, "En el país existen diferentes alternativas para ahorrar e invertir, como cuentas AFC (Ahorro para el Fomento a la Construcción), fondos de pensiones voluntarias, CDT (Certificados de Depósito a Término) y más. Conocer estas opciones te permitirá tomar decisiones estratégicas que se ajusten a tus necesidades y objetivos financieros."));
        consejos.add(new Consejos(null, "Aplicaciones como Fintonic, Wallet, Mint y muchas otras te ayudan a controlar tus finanzas de manera práctica. Configura alertas de pago, lleva un registro detallado de tus ingresos y gastos y analiza tus hábitos de consumo para mejorar tu administración financiera."));
        consejos.add(new Consejos(null, "Ahorrar con un propósito específico te motiva a mantener la disciplina. Define objetivos claros como la compra de una casa, la educación de tus hijos o un viaje soñado. También destina una parte de tus ingresos para imprevistos que puedan surgir en el futuro."));
        consejos.add(new Consejos(null, "Antes de hacer compras costosas, compara precios, investiga opciones y evalúa si realmente lo necesitas. Evita compras impulsivas esperando unos días antes de tomar la decisión final. Usa estrategias como las listas de compras y la regla de las 24 o 48 horas para reducir gastos innecesarios.."));
        consejos.add(new Consejos(null, "La inflación reduce el valor del dinero con el tiempo. Para proteger tus ahorros, busca opciones que ofrezcan rendimientos por encima de la inflación, como inversiones en renta fija o variable. Mantente informado sobre la economía y ajusta tus decisiones financieras en función de los cambios del mercado."));
        consejos.add(new Consejos(null, "Muchas instituciones financieras ofrecen beneficios como tasas preferenciales, programas de puntos, cashback y descuentos en establecimientos. Revisa las promociones disponibles en tus bancos y aprovecha las que realmente sean útiles para ti sin caer en gastos innecesarios."));
        consejos.add(new Consejos(null, "Consulta fuentes oficiales y entidades financieras para obtener información confiable."));
        consejos.add(new Consejos(null, "Tu historial crediticio es clave para acceder a préstamos y créditos con buenas condiciones. Evita atrasos en tus pagos, usa el crédito de manera responsable y revisa tu reporte de crédito periódicamente para detectar errores y corregirlos a tiempo."));
        consejos.add(new Consejos(null, "Compartir conocimientos financieros con tu familia y amigos ayuda a crear un entorno más saludable económicamente. Enseña a los más jóvenes sobre la importancia del ahorro y la planificación financiera, y promueve hábitos responsables en tu comunidad."));


        return consejos;
    }

    public List<Consejos> obtenerConsejosAleatorios() {
        return repositorioConsejos.findRandomConsejos();
    }


}

