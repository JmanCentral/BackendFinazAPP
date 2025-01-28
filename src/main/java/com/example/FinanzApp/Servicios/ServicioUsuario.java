package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.UsuarioDTO;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.io.Serializable;

@Data
@Service
public class ServicioUsuario implements Serializable {

    private ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ServicioUsuario(ModelMapper modelMapper, RepositorioUsuario repositorioUsuario , PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.repositorioUsuario = repositorioUsuario;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    private final RepositorioUsuario repositorioUsuario;

    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        if (repositorioUsuario.findByUsername(usuarioDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        Usuario nuevoUsuario = modelMapper.map(usuarioDTO, Usuario.class);
        nuevoUsuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena())); // Encriptar

        Usuario usuarioGuardado = repositorioUsuario.save(nuevoUsuario);
        return modelMapper.map(usuarioGuardado, UsuarioDTO.class);
    }


}