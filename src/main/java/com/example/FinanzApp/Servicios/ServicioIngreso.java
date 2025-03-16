package com.example.FinanzApp.Servicios;


import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.Entidades.Ingreso;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioIngreso;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
public class ServicioIngreso implements Serializable {

    private ModelMapper modelMapper;
    private final RepositorioIngreso repositorioIngreso;
    private final RepositorioUsuario repositorioUsuario;


    public IngresoDTO RegistrarIngreso(IngresoDTO ingresoDTO, Long usuarioId) {
        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Ingreso nuevoIngreso = modelMapper.map(ingresoDTO, Ingreso.class);
        nuevoIngreso.setUsuario(usuario);

        Ingreso ingresoGuardado = repositorioIngreso.save(nuevoIngreso);

        return modelMapper.map(ingresoGuardado, IngresoDTO.class);
    }

    public List<IngresoDTO> BuscarIngresosCasualesPorAnio(Long id_usuario){

        List<Ingreso> ingresos = repositorioIngreso.verificacion(id_usuario);

        return ingresos.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .collect(Collectors.toList());

    }


    public List<IngresoDTO> BuscarIngresosMensuales(Long id_usuario){

        List<Ingreso> ingresos = repositorioIngreso.findIngresosMensualesByUsuarioId(id_usuario);

        return ingresos.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .collect(Collectors.toList());

    }

    public List<IngresoDTO> BuscarIngresosCasuales(Long id_usuario){

        List<Ingreso> ingresos = repositorioIngreso.findIngresosCasualesDelMes(id_usuario);

        return ingresos.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .collect(Collectors.toList());
    }

    public Double BuscarIngresosTotales(Long id_usuario) {
        // Consultar el total de ingresos directamente desde el repositorio
        Double totalIngresos = repositorioIngreso.getIngTotalDeEsteMes(id_usuario);

        // Manejo de posibles valores nulos
        if (totalIngresos == null) {
            totalIngresos = 0.0;
        }

        return totalIngresos;
    }

    public List<IngresoDTO> BuscarIngresosMensuales(Long usuarioId, Integer anio, Integer mes) {
        List<Ingreso> ingresosMensuales = repositorioIngreso.getIngresosMensuales(usuarioId, anio, mes);


        return ingresosMensuales.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .collect(Collectors.toList());
    }

    public IngresoDTO ModificarIngreso(Long id_ingreso, IngresoDTO ingresoDTO) {
        // Buscar el gasto por su ID en el repositorio
        Optional<Ingreso> ingresoOptional = repositorioIngreso.findById(id_ingreso);

        // Validar si el gasto existe
        if (ingresoOptional.isPresent()) {
            Ingreso ingreso = ingresoOptional.get();

            // Actualizar los campos del gasto con los datos del DTO
            ingreso.setNombre_ingreso(ingresoDTO.getNombre_ingreso());
            ingreso.setTipo_ingreso(ingresoDTO.getTipo_ingreso());
            ingreso.setValor(ingresoDTO.getValor());
            ingreso.setFecha(ingresoDTO.getFecha());

            // Guardar los cambios en el repositorio
            Ingreso ingresoActualizado = repositorioIngreso.save(ingreso);

            // Convertir la entidad actualizada de nuevo en un DTO para retornarlo
            IngresoDTO ingresoActualizadoDTO = new IngresoDTO();
            ingresoActualizadoDTO.setId_ingreso(ingresoActualizado.getId_ingreso());
            ingresoActualizadoDTO.setNombre_ingreso(ingresoActualizado.getNombre_ingreso());
            ingresoActualizadoDTO.setTipo_ingreso(ingresoActualizado.getTipo_ingreso());
            ingresoActualizadoDTO.setValor(ingresoActualizado.getValor());
            ingresoActualizadoDTO.setFecha(ingresoActualizado.getFecha());

            return  ingresoActualizadoDTO;
        } else {
            // Lanza una excepción si el gasto no existe
            throw new RuntimeException("El gasto con ID " + id_ingreso + " no existe.");
        }
    }
    public Double ProyectarIngresos(Long id_usuario){

        Double totalIngresos =  repositorioIngreso.calcularTotalMensual(id_usuario);

        return totalIngresos;
    }
    public Double AhorroMensual (Long id_usuario) {

        Double totalingresos = repositorioIngreso.calcularAhorroPosible(id_usuario);

        return totalingresos;
    }
    public void eliminarIngreso(Long id) {
        repositorioIngreso.deleteById(id);
    }
}
