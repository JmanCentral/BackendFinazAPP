package com.example.finanzapp.Servicios;

import com.example.finanzapp.DTOS.CalificacionDTO;
import com.example.finanzapp.Entidades.Calificacion;
import com.example.finanzapp.Entidades.Consejos;
import com.example.finanzapp.Entidades.Usuario;
import com.example.finanzapp.Repositorios.RepositorioCalificacion;
import com.example.finanzapp.Repositorios.RepositorioConsejos;
import com.example.finanzapp.Repositorios.RepositorioUsuario;
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

    //Inyeccción de dependencias
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioConsejos repositorioConsejos;
    private final RepositorioCalificacion repositorioCalificacion;
    private ModelMapper modelMapper;


    /**
     * Registra una calificación (me gusta o no me gusta) de un usuario para un consejo específico.
     *
     * Validaciones realizadas:
     * - Verifica que el usuario y el consejo existan en la base de datos.
     * - Evita que el mismo usuario dé múltiples "me gusta" o "no me gusta" al mismo consejo.
     *
     * Si la validación es exitosa, persiste la calificación en la base de datos.
     *
     * @param calificacionDTO Objeto con la información de la calificación.
     * @return Calificación registrada en formato {@link CalificacionDTO}.
     * @throws RuntimeException Si el usuario o consejo no existen, o si la calificación ya fue registrada.
     */
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

    /**
     * Lista todas las calificaciones registradas en el sistema.
     *
     * Mapea cada entidad {@link Calificacion} a su correspondiente {@link CalificacionDTO}
     * incluyendo el ID del usuario y el ID del consejo asociado (si aplica).
     *
     * @return Lista de todas las calificaciones registradas.
     */
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
