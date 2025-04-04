package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.ConsejosDTO;

import com.example.FinanzApp.Entidades.Consejos;
import com.example.FinanzApp.Repositorios.RepositorioConsejos;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ServicioConsejos {


    private RepositorioConsejos repositorioConsejos;

    private ModelMapper modelMapper;


    public ConsejosDTO RegistrarConsejos (ConsejosDTO consejosDTO) {

        Consejos nuevoConsejo = modelMapper.map(consejosDTO, Consejos.class);

        Consejos nuevoConsejos = repositorioConsejos.save(nuevoConsejo);

        return modelMapper.map(nuevoConsejos, ConsejosDTO.class);

    }


    public List<ConsejosDTO> obtenerConsejosAleatorios() {

        List<Consejos> consejos = repositorioConsejos.findAll();

        Collections.shuffle(consejos);

        return consejos.stream()
                .map(consejo -> modelMapper.map(consejo, ConsejosDTO.class))
                .toList();
    }
}

