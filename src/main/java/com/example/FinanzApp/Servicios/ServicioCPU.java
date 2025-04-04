package com.example.FinanzApp.Servicios;

import org.springframework.stereotype.Service;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServicioCPU {

    private final OperatingSystemMXBean osBean;

    public ServicioCPU() {
        this.osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

    public void imprimirUsoCpu() {
        long tiempoCpuUsado = osBean.getProcessCpuTime(); // Tiempo de CPU usado en nanosegundos
        long tiempoTotal = System.nanoTime(); // Tiempo total del sistema en nanosegundos

        double usoCpu = ((double) tiempoCpuUsado / tiempoTotal) * 100; // Cálculo del uso de CPU

        // Imprimir valores en logs
        log.info("Tiempo de CPU usado: {} ns", tiempoCpuUsado);
        log.info("Tiempo total: {} ns", tiempoTotal);
        log.info("Uso de CPU calculado: {}%", usoCpu);
    }
}
