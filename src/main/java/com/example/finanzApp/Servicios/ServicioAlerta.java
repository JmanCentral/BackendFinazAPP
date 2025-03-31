package com.example.finanzApp.Servicios;

import com.example.finanzApp.DTOS.AlertaDTO;
import com.example.finanzApp.Entidades.Alerta;
import com.example.finanzApp.Entidades.Usuario;
import com.example.finanzApp.Repositorios.RepositorioAlerta;
import com.example.finanzApp.Repositorios.RepositorioGasto;
import com.example.finanzApp.Repositorios.RepositorioIngreso;
import com.example.finanzApp.Repositorios.RepositorioUsuario;
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


    private final RepositorioAlerta repositorioAlerta;
    private ModelMapper modelMapper;
    private  final RepositorioGasto repositorioGasto;
    private final RepositorioIngreso repositorioIngreso;
    private final RepositorioUsuario repositorioUsuario;


    public AlertaDTO RegistrarAlerta (AlertaDTO alertaDTO , Long  usuarioId) {

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alerta nuevaAlerta = modelMapper.map(alertaDTO, Alerta.class);
        nuevaAlerta.setUsuario(usuario);

        Alerta AlertaGuardada = repositorioAlerta.save(nuevaAlerta);

        return modelMapper.map(AlertaGuardada, AlertaDTO.class);

    }

    public List<AlertaDTO> ObtenerAlerta(Long id_usuario) {

        List<Alerta> alertas  = repositorioAlerta.getAlertasPorUsuario(id_usuario);

        return alertas.stream()
                .map(alerta -> modelMapper.map(alerta, AlertaDTO.class))
                .collect(Collectors.toList());

    }

    public List<AlertaDTO> ObtenerAlertaFecha(Long id_usuario) {

        List<Alerta> alertas  = repositorioAlerta.getAlertasDeEsteAno(id_usuario);

        return alertas.stream()
                .map(alerta -> modelMapper.map(alerta, AlertaDTO.class))
                .collect(Collectors.toList());

    }

    public List<AlertaDTO> ObtenerAlertaEsteMes(Long id_usuario) {

        List<Alerta> alertas  = repositorioAlerta.getAlertasDeEsteMes(id_usuario);

        return alertas.stream()
                .map(alerta -> modelMapper.map(alerta, AlertaDTO.class))
                .collect(Collectors.toList());

    }

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

    public void EliminarAlerta (Long id_alerta){

        repositorioAlerta.deleteById(id_alerta);

    }

}