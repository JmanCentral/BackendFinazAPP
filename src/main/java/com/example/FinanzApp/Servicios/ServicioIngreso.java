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
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class ServicioIngreso implements Serializable {

    private ModelMapper modelMapper;

    @Autowired
    public ServicioIngreso(ModelMapper modelMapper, RepositorioIngreso repositorioIngreso , RepositorioUsuario repositorioUsuario) {
        this.modelMapper = modelMapper;
        this.repositorioIngreso = repositorioIngreso;
        this.repositorioUsuario = repositorioUsuario;
    }

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

    public List<IngresoDTO> BuscarIngresosMensuales(Long id_usuario){

        List<Ingreso> ingresos = repositorioIngreso.findIngresosMensualesByUsuarioId(id_usuario);

        return ingresos.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .collect(Collectors.toList());

    }










}
