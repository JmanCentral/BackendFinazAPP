package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.CategoriaTotalDTO;
import com.example.FinanzApp.DTOS.GastoDTO;
import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.Entidades.Gasto;
import com.example.FinanzApp.Entidades.Ingreso;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioGasto;
import com.example.FinanzApp.Repositorios.RepositorioIngreso;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import jakarta.persistence.Tuple;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class ServicioGasto {

    private ModelMapper modelMapper;
    private  final RepositorioGasto repositorioGasto;
    private final RepositorioIngreso repositorioIngreso;
    private final RepositorioUsuario repositorioUsuario;


    @Autowired
    public ServicioGasto(ModelMapper modelMapper,RepositorioGasto repositorioGasto, RepositorioIngreso repositorioIngreso , RepositorioUsuario repositorioUsuario) {
        this.modelMapper = modelMapper;
        this.repositorioGasto = repositorioGasto;
        this.repositorioIngreso = repositorioIngreso;
        this.repositorioUsuario = repositorioUsuario;
    }


    public GastoDTO RegistrarGasto(GastoDTO gastoDTO, Long usuarioId) {

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Gasto nuevoGasto = modelMapper.map(gastoDTO, Gasto.class);
        nuevoGasto.setUsuario(usuario);

        Gasto gastoGuardado = repositorioGasto.save(nuevoGasto);

        return modelMapper.map(gastoGuardado, GastoDTO.class);
    }

    public Double ObtenerDisponible(Long id_usuario){

        return repositorioGasto.getDisponible(id_usuario);

    }

    public List<GastoDTO> BuscarGastosMesCategoria(Long id_usuario , String categoria){

        List<Gasto> gastos  = repositorioGasto.getGastosMesCategoria(id_usuario , categoria);

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .collect(Collectors.toList());

    }

    public Double ObtenerValorGastosMesCategoria (Long id_usuario , String categoria){

        return repositorioGasto.getValorGastosMesCategoria(id_usuario , categoria);

    }

    public Double ValorGastosMes (Long id_usuario){

        return repositorioGasto.getValorGastosMes(id_usuario);

    }


    public List<GastoDTO> BuscarGastosPorFechas(Long id_usuario , LocalDate fechaInf , LocalDate fechaSup){

        List<Gasto> gastos  = repositorioGasto.getGastosPorFechas(id_usuario , fechaInf , fechaSup );

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .collect(Collectors.toList());

    }

    public List<GastoDTO> OrdenarAscendentemente(Long id_usuario){

        List<Gasto> gastos  = repositorioGasto.findByUsuarioIdOrderByValorAsc(id_usuario);

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .collect(Collectors.toList());

    }

    public List<GastoDTO> OrdenarDescendentemente(Long id_usuario){

        List<Gasto> gastos  = repositorioGasto.findByUsuarioIdOrderByValorDesc(id_usuario);

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .collect(Collectors.toList());

    }

    public GastoDTO OrdenarPorValorAlto(Long id_usuario){

        Gasto gastos  = repositorioGasto.getValorMasAlto(id_usuario);

        return modelMapper.map(gastos, GastoDTO.class);

    }

    public GastoDTO OrdenarPorValorBajo(Long id_usuario){

        Gasto gastos  = repositorioGasto.getValorMasBajo(id_usuario);

        return modelMapper.map(gastos, GastoDTO.class);

    }

    public Double ObtenerPromedioDeGastos(Long id_usuario){

        return  repositorioGasto.getPromedioGastosMes(id_usuario);

    }

    public String ObtenerGastoRecurrente (Long id_usuario){

        return repositorioGasto.getDescripcionRecurrente(id_usuario);

    }

    public Double PorcentajeGastosSobreIngresos (Long id_usuario) {

        return repositorioGasto.getPorcentajeGastosSobreIngresos(id_usuario);

    }

    public CategoriaTotalDTO ordenarPorCategoriaMasAlta(Long idUsuario) {

        Object[] resultados = repositorioGasto.getCategoriaConMasGastos(idUsuario);

        if (resultados != null && resultados.length == 2) {
            String categoria = (String) resultados[0]; // Primera columna
            Double totalValor = (Double) resultados[1]; // Segunda columna
            return new CategoriaTotalDTO(categoria, totalValor);
        } else {
            return null;
        }
    }


    public Double ObtenerPromedioDiario (Long id_usuario) {

        return repositorioGasto.getGastoPromedioDiarioTotal(id_usuario);

    }


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


    public void EliminarGasto (Long id_gasto){

        repositorioGasto.deleteById(id_gasto);
    }

}
