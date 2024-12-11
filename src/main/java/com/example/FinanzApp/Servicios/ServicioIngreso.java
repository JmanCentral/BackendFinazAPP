package com.example.FinanzApp.Servicios;


import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.Entidades.Ingreso;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioIngreso;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class ServicioIngreso implements Serializable {

    private ModelMapper modelMapper;
    private final RepositorioIngreso repositorioIngreso;
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioIngreso(ModelMapper modelMapper, RepositorioIngreso repositorioIngreso , RepositorioUsuario repositorioUsuario) {
        this.modelMapper = modelMapper;
        this.repositorioIngreso = repositorioIngreso;
        this.repositorioUsuario = repositorioUsuario;
    }


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

    public void modificarIngresos(String nombre , Date fecha , Double valor , Long id_ingreso) {

        repositorioIngreso.modificarIngreso(nombre, fecha, valor, id_ingreso);

    }

    public Double ProyectarIngresos(Long id_usuario){

        Double totalIngresos =  repositorioIngreso.calcularTotalMensual(id_usuario);

        return totalIngresos;

    }



    public void eliminarIngreso(Long id) {
        repositorioIngreso.deleteById(id);
    }



}
