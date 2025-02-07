package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.AlertaDTO;
import com.example.FinanzApp.DTOS.GastoDTO;
import com.example.FinanzApp.Entidades.Alerta;
import com.example.FinanzApp.Entidades.Gasto;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioAlerta;
import com.example.FinanzApp.Repositorios.RepositorioGasto;
import com.example.FinanzApp.Repositorios.RepositorioIngreso;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class ServicioAlerta {


    private final RepositorioAlerta repositorioAlerta;
    private ModelMapper modelMapper;
    private  final RepositorioGasto repositorioGasto;
    private final RepositorioIngreso repositorioIngreso;
    private final RepositorioUsuario repositorioUsuario;


    @Autowired
    public ServicioAlerta(RepositorioAlerta repositorioAlerta,
                          ModelMapper modelMapper,
                          RepositorioGasto repositorioGasto,
                          RepositorioIngreso repositorioIngreso,
                          RepositorioUsuario repositorioUsuario) {
        this.repositorioAlerta = repositorioAlerta;
        this.modelMapper = modelMapper;
        this.repositorioGasto = repositorioGasto;
        this.repositorioIngreso = repositorioIngreso;
        this.repositorioUsuario = repositorioUsuario;
    }

    public AlertaDTO RegistrarAlerta (AlertaDTO alertaDTO ,Long  usuarioId) {

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
