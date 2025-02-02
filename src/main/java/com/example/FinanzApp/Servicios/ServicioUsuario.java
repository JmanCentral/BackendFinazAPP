package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.UsuarioDTO;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.util.Optional;

@Data
@Service
public class ServicioUsuario implements Serializable {

    private ModelMapper modelMapper;

    @Autowired
    public ServicioUsuario(ModelMapper modelMapper, RepositorioUsuario repositorioUsuario) {
        this.modelMapper = modelMapper;
        this.repositorioUsuario = repositorioUsuario;
    }

    private final RepositorioUsuario repositorioUsuario;

    public UsuarioDTO registrarUsuario (UsuarioDTO usuarioDTO) {

        if (repositorioUsuario.findByUsername(usuarioDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        Usuario nuevoUsuario = modelMapper.map(usuarioDTO, Usuario.class);

        nuevoUsuario.setUsername(usuarioDTO.getUsername());
        nuevoUsuario.setNombre(usuarioDTO.getNombre());
        nuevoUsuario.setApellido(usuarioDTO.getApellido());
        nuevoUsuario.setPassword(usuarioDTO.getPassword());


        Usuario usuarioGuardado = repositorioUsuario.save(nuevoUsuario);
        return modelMapper.map(usuarioGuardado, UsuarioDTO.class);


    }

    public UsuarioDTO obtenerUusarioPorID (long id_uuario) {

        Optional<Usuario> usuario = repositorioUsuario.findById(id_uuario);

        if (usuario.isPresent()) {

            return modelMapper.map(usuario.get(), UsuarioDTO.class);
        } else {

            return null;
        }

    }

    public UsuarioDTO verificarUusario (String username , String password) {

        Optional<Usuario> usuarios = repositorioUsuario.findByUsernameAndPassword(username, password);

        if (usuarios.isEmpty()) {
            return null;
        }

        Usuario user = usuarios.get();
        return modelMapper.map(user , UsuarioDTO.class);

    }

}
