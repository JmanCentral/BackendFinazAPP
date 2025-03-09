package com.example.FinanzApp.Servicios;


import com.example.FinanzApp.DTOS.RecordatorioDTO;
import com.example.FinanzApp.Entidades.Recordatorio;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioRecordatorio;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import jakarta.transaction.Transactional;
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
public class ServicioRecordatorio {

    private final RepositorioRecordatorio repositorioRecordatorio;
    private final RepositorioUsuario repositorioUsuario;
    private ModelMapper modelMapper;

    public RecordatorioDTO RegistrarRecordatorio(RecordatorioDTO recordatorioDTO, Long usuarioId) {

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Recordatorio nuevoRecordatorio = modelMapper.map(recordatorioDTO, Recordatorio.class);
        nuevoRecordatorio.setUsuario(usuario);

        Recordatorio RecordatorioGuardada = repositorioRecordatorio.save(nuevoRecordatorio);

        return modelMapper.map(RecordatorioGuardada, RecordatorioDTO.class);

    }

    public List<RecordatorioDTO> ListarRecordatorios(Long usuarioId) {

        List<Recordatorio> recordatorios = repositorioRecordatorio.findByUsuarioId(usuarioId);

        return recordatorios.stream()
                .map(recordatorio -> modelMapper.map(recordatorio, RecordatorioDTO.class))
                .collect(Collectors.toList());

    }


    public RecordatorioDTO ModificarRecordatorio(Long id_recordatorio, RecordatorioDTO recordatorioDTO) {
        // Buscar el recordatorio por su ID en el repositorio
        Optional<Recordatorio> recordatorioOptional = repositorioRecordatorio.findById(id_recordatorio);

        // Validar si el recordatorio existe
        if (recordatorioOptional.isPresent()) {
            Recordatorio recordatorio = recordatorioOptional.get();

            // Actualizar los campos del recordatorio con los datos del DTO
            recordatorio.setNombre(recordatorioDTO.getNombre());
            recordatorio.setEstado(recordatorioDTO.getEstado());
            recordatorio.setFecha(recordatorioDTO.getFecha());
            recordatorio.setDias_recordatorio(recordatorioDTO.getDias_recordatorio());
            recordatorio.setValor(recordatorioDTO.getValor());

            // Guardar los cambios en el repositorio
            Recordatorio recordatorioActualizado = repositorioRecordatorio.save(recordatorio);

            // Convertir la entidad actualizada en un DTO para retornarlo
            RecordatorioDTO recordatorioActualizadoDTO = new RecordatorioDTO();
            recordatorioActualizadoDTO.setId_recordatorio(recordatorioActualizado.getId_recordatorio());
            recordatorioActualizadoDTO.setNombre(recordatorioActualizado.getNombre());
            recordatorioActualizadoDTO.setEstado(recordatorioActualizado.getEstado());
            recordatorioActualizadoDTO.setFecha(recordatorioActualizado.getFecha());
            recordatorioActualizadoDTO.setDias_recordatorio((recordatorioActualizado.getDias_recordatorio()));
            recordatorioActualizadoDTO.setValor(recordatorioActualizado.getValor());

            return recordatorioActualizadoDTO;
        } else {
            // Lanza una excepción si el recordatorio no existe
            throw new RuntimeException("El recordatorio con ID " + id_recordatorio + " no existe.");
        }
    }

        public void EliminarRecordatorio (Long id_recordatorio){

            repositorioRecordatorio.deleteById(id_recordatorio);

        }

    @Transactional
        public void eliminarTodosLosRecordatorios(Long id_usuario) {
        repositorioRecordatorio.deleteByUsuario(id_usuario);
    }

    public  List<RecordatorioDTO> BuscarPorNombre(String nombre) {
        List<Recordatorio> recordatorios = repositorioRecordatorio.findByNombre(nombre);

        return recordatorios.stream()
                .map(recordatorio -> modelMapper.map(recordatorio, RecordatorioDTO.class))
                .collect(Collectors.toList());
    }

}


