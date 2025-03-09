package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.CalificacionDTO;
import com.example.FinanzApp.Entidades.Calificacion;
import com.example.FinanzApp.Entidades.Consejos;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioCalificacion;
import com.example.FinanzApp.Repositorios.RepositorioConsejos;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ServicioCalificacion {

    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioConsejos repositorioConsejos;
    private final RepositorioCalificacion repositorioCalificacion;
    private ModelMapper modelMapper;

    public CalificacionDTO registrarCalificacion(CalificacionDTO calificacionDTO) {

        Long idUsuario = calificacionDTO.getId_usuario();
        Long idConsejo = calificacionDTO.getIdConsejo();

        Usuario usuario = repositorioUsuario.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Consejos consejo = repositorioConsejos.findById(idConsejo)
                .orElseThrow(() -> new RuntimeException("Consejo no encontrado"));


        Calificacion calificacion = modelMapper.map(calificacionDTO, Calificacion.class);

        calificacion.setUsuario(usuario);
        calificacion.setConsejos(consejo);

        Calificacion calificacionGuardada = repositorioCalificacion.save(calificacion);

        return modelMapper.map(calificacionGuardada, CalificacionDTO.class);
    }

    public List<CalificacionDTO> listarCalificaciones(Long idConsejo) {

        List<Calificacion> calificaciones = repositorioCalificacion.findByConsejos(idConsejo);

        return calificaciones.stream()
                .map(calificacion -> modelMapper.map(calificacion, CalificacionDTO.class))
                .collect(Collectors.toList());
    }


}
