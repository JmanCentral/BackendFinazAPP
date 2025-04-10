package com.example.finanzapp.Servicios;

import com.example.finanzapp.DTOS.IngresoDTO;
import com.example.finanzapp.Entidades.Ingreso;
import com.example.finanzapp.Entidades.Usuario;
import com.example.finanzapp.Repositorios.RepositorioIngreso;
import com.example.finanzapp.Repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
@Data
@Service
@AllArgsConstructor
public class ServicioIngreso implements Serializable {

    //Inyección de dependencias
    private ModelMapper modelMapper;  // Utilizado para mapear entre entidades y DTOs
    private final RepositorioIngreso repositorioIngreso; // Repositorio para operaciones con ingresos
    private final RepositorioUsuario repositorioUsuario;  // Repositorio para verificar la existencia del usuario

    /**
     * Registra un nuevo ingreso asociado a un usuario.
     *
     * @param ingresoDTO Datos del ingreso a registrar.
     * @param usuarioId ID del usuario al que se asociará el ingreso.
     * @return IngresoDTO con los datos del ingreso guardado.
     */
    public IngresoDTO RegistrarIngreso(IngresoDTO ingresoDTO, Long usuarioId) {

        //Buscar al usuario correspondiente para el ingreso
        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Ingreso nuevoIngreso = modelMapper.map(ingresoDTO, Ingreso.class);
        nuevoIngreso.setUsuario(usuario);

        Ingreso ingresoGuardado = repositorioIngreso.save(nuevoIngreso);

        return modelMapper.map(ingresoGuardado, IngresoDTO.class);
    }

    /**
     * Busca ingresos casuales de un usuario a lo largo del año.
     *
     * @param id_usuario ID del usuario.
     * @return Lista de ingresos casuales en formato DTO.
     */
    public List<IngresoDTO> BuscarIngresosCasualesPorAnio(Long id_usuario){

        List<Ingreso> ingresos = repositorioIngreso.verificacion(id_usuario);

        return ingresos.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .toList();
    }

    /**
     * Busca ingresos mensuales de un usuario en el mes actual.
     *
     * @param id_usuario ID del usuario.
     * @return Lista de ingresos mensuales en formato DTO.
     */
    public List<IngresoDTO> BuscarIngresosMensuales(Long id_usuario){

        List<Ingreso> ingresos = repositorioIngreso.findIngresosMensualesByUsuarioId(id_usuario);

        return ingresos.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .toList();

    }

    /**
     * Busca ingresos casuales del mes actual para un usuario.
     *
     * @param id_usuario ID del usuario.
     * @return Lista de ingresos casuales del mes en formato DTO.
     */
    public List<IngresoDTO> BuscarIngresosCasuales(Long id_usuario){

        List<Ingreso> ingresos = repositorioIngreso.findIngresosCasualesDelMes(id_usuario);

        return ingresos.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .toList();
    }

    /**
     * Obtiene el total de ingresos del mes actual para un usuario.
     *
     * @param id_usuario ID del usuario.
     * @return Suma total de ingresos del mes.
     */
    public Double BuscarIngresosTotales(Long id_usuario) {
        // Consultar el total de ingresos directamente desde el repositorio
        Double totalIngresos = repositorioIngreso.getIngTotalDeEsteMes(id_usuario);

        // Manejo de posibles valores nulos
        if (totalIngresos == null) {
            totalIngresos = 0.0;
        }

        return totalIngresos;
    }

    /**
     * Busca ingresos mensuales de un usuario en un mes y año específicos.
     *
     * @param usuarioId ID del usuario.
     * @param anio Año deseado.
     * @param mes Mes deseado.
     * @return Lista de ingresos mensuales en formato DTO.
     */
    public List<IngresoDTO> BuscarIngresosMensuales(Long usuarioId, Integer anio, Integer mes) {
        List<Ingreso> ingresosMensuales = repositorioIngreso.getIngresosMensuales(usuarioId, anio, mes);


        return ingresosMensuales.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .toList();
    }

    /**
     * Modifica un ingreso existente según su ID.
     *
     * @param id_ingreso ID del ingreso a modificar.
     * @param ingresoDTO Nuevos datos para el ingreso.
     * @return IngresoDTO con los datos actualizados.
     */

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

    /**
     * Proyecta el ingreso total mensual del usuario.
     *
     * @param id_usuario ID del usuario.
     * @return Valor proyectado de ingresos para el mes.
     */
    public Double ProyectarIngresos(Long id_usuario){

        return repositorioIngreso.calcularTotalMensual(id_usuario);
    }

    /**
     * Calcula el ahorro mensual posible según ingresos actuales.
     *
     * @param id_usuario ID del usuario.
     * @return Valor estimado de ahorro posible.
     */
    public Double AhorroMensual (Long id_usuario) {

        return repositorioIngreso.calcularAhorroPosible(id_usuario);
    }

    /**
     * Elimina un ingreso por su ID.
     *
     * @param id ID del ingreso a eliminar.
     */
    public void eliminarIngreso(Long id) {
        repositorioIngreso.deleteById(id);
    }
}
