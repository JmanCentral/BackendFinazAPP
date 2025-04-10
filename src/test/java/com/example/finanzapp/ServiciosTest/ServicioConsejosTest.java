package com.example.finanzapp.ServiciosTest;

import com.example.finanzapp.DTOS.ConsejosDTO;
import com.example.finanzapp.Entidades.Consejos;
import com.example.finanzapp.Repositorios.RepositorioConsejos;
import com.example.finanzapp.Servicios.ServicioConsejos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioConsejosTest {

    @InjectMocks
    private ServicioConsejos servicioConsejos;

    @Mock
    private RepositorioConsejos repositorioConsejos;

    @Mock
    private ModelMapper modelMapper;

    private ConsejosDTO consejosDTO;
    private Consejos consejos;
    private Consejos consejosGuardado;

    @BeforeEach
    void setUp() {
        consejosDTO = new ConsejosDTO();
        consejosDTO.setIdConsejo(1L);
        consejosDTO.setConsejo("Ahorra el 10% de tus ingresos.");

        consejos = new Consejos();
        consejos.setIdConsejo(1L);
        consejos.setConsejo("Ahorra el 10% de tus ingresos.");

        consejosGuardado = new Consejos();
        consejosGuardado.setIdConsejo(1L);
        consejosGuardado.setConsejo("Ahorra el 10% de tus ingresos.");
    }

    @Test
    void testRegistrarConsejos() {
        when(modelMapper.map(consejosDTO, Consejos.class)).thenReturn(consejos);
        when(repositorioConsejos.save(consejos)).thenReturn(consejosGuardado);
        when(modelMapper.map(consejosGuardado, ConsejosDTO.class)).thenReturn(consejosDTO);

        ConsejosDTO resultado = servicioConsejos.RegistrarConsejos(consejosDTO);

        assertNotNull(resultado);
        assertEquals(consejosDTO.getIdConsejo(), resultado.getIdConsejo());
        assertEquals(consejosDTO.getConsejo(), resultado.getConsejo());

        verify(modelMapper).map(consejosDTO, Consejos.class);
        verify(repositorioConsejos).save(consejos);
        verify(modelMapper).map(consejosGuardado, ConsejosDTO.class);
    }

    @Test
    void testObtenerConsejosAleatorios() {

        Consejos consejo1 = new Consejos();
        Consejos consejo2 = new Consejos();
        List<Consejos> consejosList = Arrays.asList(consejo1, consejo2);

        ConsejosDTO consejoDTO1 = new ConsejosDTO(1L, "Ahorra dinero.");
        ConsejosDTO consejoDTO2 = new ConsejosDTO(2L, "Invierte en educación.");

        when(repositorioConsejos.findAll()).thenReturn(consejosList);
        when(modelMapper.map(consejo1, ConsejosDTO.class)).thenReturn(consejoDTO1);
        when(modelMapper.map(consejo2, ConsejosDTO.class)).thenReturn(consejoDTO2);

        List<ConsejosDTO> resultado = servicioConsejos.obtenerConsejosAleatorios();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(repositorioConsejos).findAll();
        verify(modelMapper, times(2)).map(any(Consejos.class), eq(ConsejosDTO.class));
    }
}
