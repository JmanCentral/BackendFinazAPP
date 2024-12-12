package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.GastoDTO;
import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.Entidades.Gasto;
import com.example.FinanzApp.Entidades.Ingreso;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioGasto;
import com.example.FinanzApp.Repositorios.RepositorioIngreso;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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


}
