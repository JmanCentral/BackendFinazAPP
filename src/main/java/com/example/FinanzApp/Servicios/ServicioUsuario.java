package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.UsuarioDTO;
import com.example.FinanzApp.Entidades.ERole;
import com.example.FinanzApp.Entidades.Roles;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioRoles;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Service
public class ServicioUsuario implements UserDetailsService {

    private ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioRoles repositorioRoles;

    @Autowired
    public ServicioUsuario(ModelMapper modelMapper, RepositorioUsuario repositorioUsuario , PasswordEncoder passwordEncoder , RepositorioRoles repositorioRoles) {
        this.modelMapper = modelMapper;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioRoles = repositorioRoles;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        if (repositorioUsuario.findByUsername(usuarioDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        Usuario nuevoUsuario = modelMapper.map(usuarioDTO, Usuario.class);
        nuevoUsuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena())); // Encriptar

        // Asignar roles
        Set<Roles> roles = usuarioDTO.getRoles().stream()
                .map(rol -> repositorioRoles.findByName(ERole.valueOf(rol))
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado")))
                .collect(Collectors.toSet());

        nuevoUsuario.setRoles(roles);

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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repositorioUsuario.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Collection<? extends GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toSet());

        return new User(usuario.getUsername(), usuario.getContrasena(), authorities);
    }
}