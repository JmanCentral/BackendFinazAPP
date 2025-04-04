package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.CategoriaTotalDTO;
import com.example.FinanzApp.DTOS.GastoDTO;
import com.example.FinanzApp.DTOS.ProyeccionDTO;
import com.example.FinanzApp.Entidades.CategoriaTotal;
import com.example.FinanzApp.Entidades.Gasto;
import com.example.FinanzApp.Entidades.GastoProjection;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioGasto;
import com.example.FinanzApp.Repositorios.RepositorioIngreso;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
public class ServicioGasto {

    //Inyección de dependencias
    private ModelMapper modelMapper;  // Utilizado para mapear entre entidades y DTOs
    private  final RepositorioGasto repositorioGasto; // Repositorio para operaciones con ingresos
    private final RepositorioIngreso repositorioIngreso; // Repositorio para verificar la existencia del ingreso
    private final RepositorioUsuario repositorioUsuario; // Repositorio para verificar la existencia del usuario


    /**
     * Registra un nuevo gasto asociado a un usuario.
     *
     * @param gastoDTO Objeto DTO con la información del gasto.
     * @param usuarioId ID del usuario al que se le asociará el gasto.
     * @return El gasto registrado en formato GastoDTO.
     * @throws RuntimeException si el usuario no es encontrado.
     */
    public GastoDTO RegistrarGasto(GastoDTO gastoDTO, Long usuarioId) {

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Gasto nuevoGasto = modelMapper.map(gastoDTO, Gasto.class);
        nuevoGasto.setUsuario(usuario);

        Gasto gastoGuardado = repositorioGasto.save(nuevoGasto);

        return modelMapper.map(gastoGuardado, GastoDTO.class);
    }

    /**
     * Obtiene el monto disponible actual de un usuario.
     *
     * @param id_usuario ID del usuario.
     * @return El monto disponible (ingresos - gastos).
     */
    public Double ObtenerDisponible(Long id_usuario){

        return repositorioGasto.getDisponible(id_usuario);

    }

    /**
     * Obtiene el monto disponible de un usuario dentro de un rango de fechas.
     *
     * @param id_usuario ID del usuario.
     * @param fechaInf Fecha inferior del rango.
     * @param fechaSup Fecha superior del rango.
     * @return El monto disponible dentro del período especificado.
     */
    public Double ObtenerDisponiblePorFechas (Long id_usuario , LocalDate fechaInf , LocalDate fechaSup ){

        return repositorioGasto.getDisponiblePorFechas(id_usuario, fechaInf, fechaSup);

    }

    /**
     * Busca los gastos del mes actual por categoría.
     *
     * @param id_usuario ID del usuario.
     * @param categoria Categoría a filtrar.
     * @return Lista de gastos en formato GastoDTO.
     */
    public List<GastoDTO> BuscarGastosMesCategoria(Long id_usuario , String categoria){

        List<Gasto> gastos  = repositorioGasto.getGastosMesCategoria(id_usuario , categoria);

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .collect(Collectors.toList());

    }

    /**
     * Obtiene el valor total de los gastos del mes actual para una categoría.
     *
     * @param id_usuario ID del usuario.
     * @param categoria Categoría de los gastos.
     * @return Valor total de los gastos de esa categoría en el mes.
     */
    public Double ObtenerValorGastosMesCategoria (Long id_usuario , String categoria){

        return repositorioGasto.getValorGastosMesCategoria(id_usuario , categoria);

    }

    /**
     * Obtiene el valor total de todos los gastos del mes actual.
     *
     * @param id_usuario ID del usuario.
     * @return Valor total de los gastos del mes.
     */
    public Double ValorGastosMes (Long id_usuario){

        return repositorioGasto.getValorGastosMes(id_usuario);

    }


    /**
     * Busca los gastos realizados por un usuario entre dos fechas.
     *
     * @param id_usuario ID del usuario.
     * @param fechaInf Fecha de inicio.
     * @param fechaSup Fecha de fin.
     * @return Lista de gastos en formato GastoDTO.
     */
    public List<GastoDTO> BuscarGastosPorFechas(Long id_usuario , LocalDate fechaInf , LocalDate fechaSup){

        List<Gasto> gastos  = repositorioGasto.getGastosPorFechas(id_usuario , fechaInf , fechaSup );

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .collect(Collectors.toList());

    }

    /**
     * Ordena los gastos del usuario de manera ascendente por valor.
     *
     * @param id_usuario ID del usuario.
     * @return Lista de gastos ordenada ascendentemente por valor.
     */
    public List<GastoDTO> OrdenarAscendentemente(Long id_usuario){

        List<Gasto> gastos  = repositorioGasto.findByUsuarioIdOrderByValorAsc(id_usuario);

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .toList();

    }

    /**
     * Ordena los gastos del usuario de manera descendente por valor.
     *
     * @param id_usuario ID del usuario.
     * @return Lista de gastos ordenada descendentemente por valor.
     */
    public List<GastoDTO> OrdenarDescendentemente(Long id_usuario){

        List<Gasto> gastos  = repositorioGasto.findByUsuarioIdOrderByValorDesc(id_usuario);

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .toList();

    }

    /**
     * Obtiene los gastos de un usuario dentro de un rango de fechas y por categoría.
     *
     * @param usuarioId ID del usuario.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @param categoria Categoría del gasto.
     * @return Lista de gastos en formato GastoDTO.
     */
    public List<GastoDTO> obtenerGastosPorRangoDeFechas(Long usuarioId, LocalDate fechaInicio, LocalDate fechaFin , String categoria) {

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Gasto> gastos  = repositorioGasto.findByUsuarioAndFechaBetweenAndCategoria(usuario , fechaInicio , fechaFin , categoria);

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .toList();

    }

    /**
     * Obtiene el gasto de mayor valor registrado por el usuario.
     *
     * @param id_usuario ID del usuario.
     * @return Gasto con el valor más alto en formato GastoDTO.
     */

        public GastoDTO OrdenarPorValorAlto(Long id_usuario){

            Gasto gastos  = repositorioGasto.getValorMasAlto(id_usuario);

            return modelMapper.map(gastos, GastoDTO.class);

        }

    /**
     * Obtiene el gasto de menor valor registrado por el usuario.
     *
     * @param id_usuario ID del usuario.
     * @return Gasto con el valor más bajo en formato GastoDTO.
     */
        public GastoDTO OrdenarPorValorBajo(Long id_usuario){

            Gasto gastos  = repositorioGasto.getValorMasBajo(id_usuario);

            return modelMapper.map(gastos, GastoDTO.class);

        }


    /**
     * Obtiene el promedio mensual de los gastos del usuario.
     *
     * @param id_usuario ID del usuario.
     * @return Promedio de los gastos del mes.
     */
        public Double ObtenerPromedioDeGastos(Long id_usuario){

            return  repositorioGasto.getPromedioGastosMes(id_usuario);

        }

    /**
     * Obtiene la descripción del gasto más recurrente del usuario.
     *
     * @param id_usuario ID del usuario.
     * @return Descripción del gasto recurrente.
     */
        public String ObtenerGastoRecurrente (Long id_usuario){

            return repositorioGasto.getDescripcionRecurrente(id_usuario);

        }

    /**
     * Calcula el porcentaje que representan los gastos sobre los ingresos del usuario.
     *
     * @param id_usuario ID del usuario.
     * @return Porcentaje de gastos sobre ingresos.
     */
        public Double PorcentajeGastosSobreIngresos (Long id_usuario) {

            return repositorioGasto.getPorcentajeGastosSobreIngresos(id_usuario);

        }

    /**
     * Calcula el promedio diario de los gastos del usuario.
     *
     * @param id_usuario ID del usuario.
     * @return Promedio diario de gastos.
     */

        public Double ObtenerPromedioDiario (Long id_usuario) {

            return repositorioGasto.getGastoPromedioDiarioTotal(id_usuario);

        }

    /**
     * Modifica un gasto existente con nuevos datos proporcionados.
     *
     * @param id_gasto ID del gasto a modificar.
     * @param gastoDTO Datos actualizados del gasto.
     * @return GastoDTO actualizado.
     * @throws RuntimeException si el gasto no existe.
     */
        public GastoDTO ModificarGasto(Long id_gasto, GastoDTO gastoDTO) {
            // Buscar el gasto por su ID en el repositorio
            Optional<Gasto> gastoOptional = repositorioGasto.findById(id_gasto);

            // Validar si el gasto existe
            if (gastoOptional.isPresent()) {
                Gasto gasto = gastoOptional.get();

                // Actualizar los campos del gasto con los datos del DTO
                gasto.setNombre_gasto(gastoDTO.getNombre_gasto());
                gasto.setCategoria(gastoDTO.getCategoria());
                gasto.setValor(gastoDTO.getValor());
                gasto.setFecha(gastoDTO.getFecha());

                // Guardar los cambios en el repositorio
                Gasto gastoActualizado = repositorioGasto.save(gasto);

                // Convertir la entidad actualizada de nuevo en un DTO para retornarlo
                GastoDTO gastoActualizadoDTO = new GastoDTO();
                gastoActualizadoDTO.setId_gasto(gastoActualizado.getId_gasto());
                gastoActualizadoDTO.setNombre_gasto(gastoActualizado.getNombre_gasto());
                gastoActualizadoDTO.setCategoria(gastoActualizado.getCategoria());
                gastoActualizadoDTO.setValor(gastoActualizado.getValor());
                gastoActualizadoDTO.setFecha(gastoActualizado.getFecha());

                return gastoActualizadoDTO;
            } else {
                // Lanza una excepción si el gasto no existe
                throw new RuntimeException("El gasto con ID " + id_gasto + " no existe.");
            }
        }

    /**
     * Elimina un gasto del repositorio a partir de su ID.
     *
     * @param id_gasto ID del gasto a eliminar.
     */
        public void EliminarGasto (Long id_gasto){

            repositorioGasto.deleteById(id_gasto);
        }

    /**
     * Lista los gastos filtrando por nombre, categoría e ID de usuario.
     *
     * @param nombre Nombre del gasto.
     * @param categoria Categoría del gasto.
     * @param id_usuario ID del usuario.
     * @return Lista de gastos en formato GastoDTO.
     */
        public List<GastoDTO> ListarPorNombres(String nombre ,String  categoria , Long id_usuario) {

            List<Gasto> gastos = repositorioGasto.findByNombreGastoAndCategoriaAndUsuarioId(nombre , categoria , id_usuario);

            return gastos.stream()
                    .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                    .toList();

        }

    /**
     * Elimina todos los gastos de una categoría específica asociados a un usuario.
     *
     * @param Categoria Categoría de los gastos a eliminar.
     * @param id_usuario ID del usuario.
     */
        @Transactional
        public void eliminarTodosLosGastos(String Categoria , Long id_usuario) {
            repositorioGasto.deleteByUsuarioIdAndCategoria(id_usuario , Categoria);
        }

    /**
     * Obtiene una lista de gastos frecuentes del usuario con descripción, cantidad y total acumulado.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de proyecciones de gastos frecuentes en formato ProyeccionDTO.
     */
        public List<ProyeccionDTO> obtenerGastosFrecuentes(Long usuarioId) {
            List<GastoProjection> gastosProjections = repositorioGasto.findGastosFrecuentes(usuarioId);

            return gastosProjections.stream()
                    .map(g -> new ProyeccionDTO(g.getDescripcion(), g.getCantidad(), g.getTotal()))
                    .toList();
        }

    /**
     * Obtiene la categoría en la que el usuario ha realizado el mayor gasto.
     *
     * @param usuarioId ID del usuario.
     * @return Categoría con mayor gasto en formato CategoriaTotalDTO.
     */
        public CategoriaTotalDTO obtenerCategoriaMasAlta(Long usuarioId) {
            CategoriaTotal resultados = repositorioGasto.getCategoriaConMasGastos(usuarioId);

            return new CategoriaTotalDTO(resultados.getCategoria(), resultados.getTotalvalor());
        }

    /**
     * Calcula la categoría con mayor gasto manualmente sumando los valores por categoría.
     *
     * @param usuarioId ID del usuario.
     * @return Categoría con mayor gasto y total gastado en formato CategoriaTotalDTO.
     */
        public CategoriaTotalDTO getCategoriaConMasGastos(Long usuarioId) {

            List<Gasto> gastos = repositorioGasto.findByUsuario(usuarioId); // Recuperar todos los gastos del usuario

            Map<String, Double> sumaPorCategoria = new HashMap<>();

            for (Gasto gasto : gastos) {
                sumaPorCategoria.put(gasto.getCategoria(),
                        sumaPorCategoria.getOrDefault(gasto.getCategoria(), 0.0) + gasto.getValor());
            }

            return sumaPorCategoria.entrySet().stream()
                    .max(Comparator.comparingDouble(Map.Entry::getValue))
                    .map(entry -> new CategoriaTotalDTO(entry.getKey(), entry.getValue()))
                    .orElse(null);
        }




}
