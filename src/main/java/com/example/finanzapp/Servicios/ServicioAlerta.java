package com.example.finanzapp.Servicios;

import com.example.finanzapp.DTOS.AlertaDTO;
import com.example.finanzapp.Entidades.Alerta;
import com.example.finanzapp.Entidades.Usuario;
import com.example.finanzapp.Repositorios.RepositorioAlerta;
import com.example.finanzapp.Repositorios.RepositorioGasto;
import com.example.finanzapp.Repositorios.RepositorioIngreso;
import com.example.finanzapp.Repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class ServicioAlerta {

    //Inyección de dependencias
    private final RepositorioAlerta repositorioAlerta; //
    private ModelMapper modelMapper; // Utilizado para mapear entre entidades y DTOs
    private final RepositorioGasto repositorioGasto; // Repositorio para operaciones con ingresos
    private final RepositorioIngreso repositorioIngreso; // Repositorio para operaciones con ingresos
    private final RepositorioUsuario repositorioUsuario; // Repositorio para operaciones con usuario


    /**
     * Registra una nueva alerta para un usuario específico.
     *
     * @param alertaDTO Datos de la alerta a registrar.
     * @param usuarioId ID del usuario asociado a la alerta.
     * @return Alerta registrada en formato AlertaDTO.
     * @throws RuntimeException si el usuario no existe.
     */
    public AlertaDTO RegistrarAlerta (AlertaDTO alertaDTO , Long  usuarioId) {

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alerta nuevaAlerta = modelMapper.map(alertaDTO, Alerta.class);
        nuevaAlerta.setUsuario(usuario);

        Alerta AlertaGuardada = repositorioAlerta.save(nuevaAlerta);

        return modelMapper.map(AlertaGuardada, AlertaDTO.class);

    }

    /**
     * Obtiene todas las alertas registradas por un usuario.
     *
     * @param id_usuario ID del usuario.
     * @return Lista de alertas en formato AlertaDTO.
     */
    public List<AlertaDTO> ObtenerAlerta(Long id_usuario) {

        List<Alerta> alertas  = repositorioAlerta.getAlertasPorUsuario(id_usuario);

        return alertas.stream()
                .map(alerta -> modelMapper.map(alerta, AlertaDTO.class))
                .collect(Collectors.toList());

    }

    /**
     * Obtiene las alertas del usuario correspondientes al año actual.
     *
     * @param id_usuario ID del usuario.
     * @return Lista de alertas del año actual en formato AlertaDTO.
     */
    public List<AlertaDTO> ObtenerAlertaFecha(Long id_usuario) {

        List<Alerta> alertas  = repositorioAlerta.getAlertasDeEsteAno(id_usuario);

        return alertas.stream()
                .map(alerta -> modelMapper.map(alerta, AlertaDTO.class))
                .collect(Collectors.toList());

    }

    /**
     * Obtiene las alertas del usuario correspondientes al mes actual.
     *
     * @param id_usuario ID del usuario.
     * @return Lista de alertas del mes actual en formato AlertaDTO.
     */
    public List<AlertaDTO> ObtenerAlertaEsteMes(Long id_usuario) {

        List<Alerta> alertas  = repositorioAlerta.getAlertasDeEsteMes(id_usuario);

        return alertas.stream()
                .map(alerta -> modelMapper.map(alerta, AlertaDTO.class))
                .collect(Collectors.toList());

    }

    /**
     * Modifica una alerta existente con nuevos datos proporcionados.
     *
     * @param id_alerta ID de la alerta a modificar.
     * @param alertaDTO Nuevos datos para la alerta.
     * @return Alerta actualizada en formato AlertaDTO.
     * @throws RuntimeException si la alerta no existe.
     */
    public AlertaDTO ModificarAlerta(Long id_alerta, AlertaDTO alertaDTO) {
        // Buscar el gasto por su ID en el repositorio
        Optional<Alerta> AlertaOptional = repositorioAlerta.findById(id_alerta);

        // Validar si la alerta existe
        if (AlertaOptional.isPresent()) {
            Alerta alerta = AlertaOptional.get();

            // Actualizar los campos del gasto con los datos del DTO
            alerta.setNombre(alertaDTO.getNombre());
            alerta.setDescripcion(alertaDTO.getDescripcion());
            alerta.setValor(alertaDTO.getValor());
            alerta.setFecha(alertaDTO.getFecha());

            // Guardar los cambios en el repositorio
            Alerta AlertaActualizada = repositorioAlerta.save(alerta);

            // Convertir la entidad actualizada de nuevo en un DTO para retornarlo
            AlertaDTO AlertaActualizadaDTO = new AlertaDTO();
            AlertaActualizadaDTO.setId_alerta(AlertaActualizada.getId_alerta());
            AlertaActualizadaDTO.setNombre(AlertaActualizada.getNombre());
            AlertaActualizadaDTO.setDescripcion(AlertaActualizada.getDescripcion());
            AlertaActualizadaDTO.setValor(AlertaActualizada.getValor());
            AlertaActualizadaDTO.setFecha(AlertaActualizada.getFecha());

            return AlertaActualizadaDTO;
        } else {
            // Lanza una excepción si el gasto no existe
            throw new RuntimeException("El gasto con ID " + alertaDTO.getId_alerta() + " no existe.");
        }
    }

    /**
     * Elimina una alerta del sistema.
     *
     * @param id_alerta ID de la alerta a eliminar.
     */
    public void EliminarAlerta (Long id_alerta){

        repositorioAlerta.deleteById(id_alerta);

    }

}