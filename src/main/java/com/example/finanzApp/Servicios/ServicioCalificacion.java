package com.example.finanzApp.Servicios;

import com.example.finanzApp.DTOS.CalificacionDTO;
import com.example.finanzApp.Entidades.Calificacion;
import com.example.finanzApp.Entidades.Consejos;
import com.example.finanzApp.Entidades.Usuario;
import com.example.finanzApp.Repositorios.RepositorioCalificacion;
import com.example.finanzApp.Repositorios.RepositorioConsejos;
import com.example.finanzApp.Repositorios.RepositorioUsuario;
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


        int likes = repositorioCalificacion.countLikesByUsuarioAndConsejo(idUsuario, idConsejo);
        int dislikes = repositorioCalificacion.countDislikesByUsuarioAndConsejo(idUsuario, idConsejo);

        if (calificacionDTO.getMe_gusta() == 1 && likes > 0) {
            throw new RuntimeException("Ya has dado 'Me gusta' a este consejo.");
        }

        if (calificacionDTO.getNo_me_gusta() == 1 && dislikes > 0) {
            throw new RuntimeException("Ya has dado 'No me gusta' a este consejo.");
        }

        // Si pasa la validación, registrar la calificación
        Calificacion calificacion = modelMapper.map(calificacionDTO, Calificacion.class);
        calificacion.setUsuario(usuario);
        calificacion.setConsejos(consejo);

        Calificacion calificacionGuardada = repositorioCalificacion.save(calificacion);

        return modelMapper.map(calificacionGuardada, CalificacionDTO.class);
    }


    public List<CalificacionDTO> listarCalificaciones() {

        List<Calificacion> calificaciones = repositorioCalificacion.findAll();

        return calificaciones.stream().map(calificacion -> {
            CalificacionDTO dto = new CalificacionDTO();
            dto.setIdCalificacion(calificacion.getIdCalificacion());
            dto.setMe_gusta(calificacion.getMe_gusta());
            dto.setNo_me_gusta(calificacion.getNo_me_gusta());
            dto.setId_usuario(calificacion.getUsuario().getId_usuario());
            dto.setIdConsejo(calificacion.getConsejos() != null ? calificacion.getConsejos().getIdConsejo() : null);
            return dto;
        }).collect(Collectors.toList());
    }



}
