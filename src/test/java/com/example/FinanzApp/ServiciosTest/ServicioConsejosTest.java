package com.example.FinanzApp.ServiciosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.example.FinanzApp.Repositorios.RepositorioConsejos;
import com.example.FinanzApp.Servicios.ServicioConsejos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ServicioConsejosTest {

    /*
    @Mock
    private RepositorioConsejos repositorioConsejos;

    @InjectMocks
    private ServicioConsejos servicioConsejos;

    private List<Consejos> consejos;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        consejos = new ArrayList<>();
        consejos.add(new Consejos("564654", "Define metas financieras claras a corto, mediano y largo plazo."));
        consejos.add(new Consejos("15525", "Registra todos tus ingresos y gastos para tener un control total de tu economía."));
    }

    @Test
    public void testInsertAllConsejos() {
        // Configuración de los mocks
        when(repositorioConsejos.saveAll(anyList())).thenReturn(consejos);

        // Ejecución del método a probar
        servicioConsejos.insertAllConsejos();

        // Verificaciones
        verify(repositorioConsejos, times(1)).saveAll(anyList());
    }


    @Test
    public void testObtenerConsejosAleatorios() {
        // Configuración de los mocks
        when(repositorioConsejos.findRandomConsejos()).thenReturn(consejos);

        // Ejecución del método a probar
        List<Consejos> resultados = servicioConsejos.obtenerConsejosAleatorios();

        // Verificaciones
        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        verify(repositorioConsejos, times(1)).findRandomConsejos();
    }

     */
}
