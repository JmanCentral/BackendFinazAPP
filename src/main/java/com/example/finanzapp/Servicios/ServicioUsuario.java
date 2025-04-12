package com.example.finanzapp.Servicios;

import com.example.finanzapp.Config.APIzeroBound;
import com.example.finanzapp.DTOS.UsuarioDTO;
import com.example.finanzapp.Entidades.ERole;
import com.example.finanzapp.Entidades.Roles;
import com.example.finanzapp.Entidades.Usuario;
import com.example.finanzapp.Excepciones.Usuario.CorreoInvalidoException;
import com.example.finanzapp.Excepciones.Usuario.EmailYaRegistradoException;
import com.example.finanzapp.Excepciones.Usuario.RolNoEncontradoException;
import com.example.finanzapp.Excepciones.Usuario.UsuarioYaRegistradoException;
import com.example.finanzapp.Repositorios.RepositorioRoles;
import com.example.finanzapp.Repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
public class ServicioUsuario implements UserDetailsService {

    // Inyección de dependencias
    private ModelMapper modelMapper; // Utilizado para mapear entre entidades y DTOs
    private final PasswordEncoder passwordEncoder;  // Para encriptar contraseñas
    private final RepositorioUsuario repositorioUsuario;  // Repositorio para operaciones CRUD de usuarios
    private final RepositorioRoles repositorioRoles; // Repositorio para obtener roles disponibles
    private final APIzeroBound apIzeroBound;

    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {


        if (repositorioUsuario.existsByEmail(usuarioDTO.getEmail())) {
            throw new EmailYaRegistradoException("El correo ya está registrado.");
        }

        if (repositorioUsuario.existsByUsername(usuarioDTO.getUsername())) {
            throw new UsuarioYaRegistradoException("El nombre de usuario ya existe.");
        }

        if (!apIzeroBound.esCorreoValido(usuarioDTO.getEmail())) {
            throw new CorreoInvalidoException("Correo electrónico inválido según ZeroBounce.");
        }

        // 3. Mapear y guardar
        Usuario nuevoUsuario = modelMapper.map(usuarioDTO, Usuario.class);
        nuevoUsuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));

        Set<Roles> roles = usuarioDTO.getRoles().stream()
                .map(rol -> repositorioRoles.findByName(ERole.valueOf(rol))
                        .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado: " + rol)))
                .collect(Collectors.toSet());

        nuevoUsuario.setRoles(roles);

        Usuario usuarioGuardado = repositorioUsuario.save(nuevoUsuario);

        return modelMapper.map(usuarioGuardado, UsuarioDTO.class);
    }


    public UsuarioDTO obtenerUusarioPorID (long id_uuario) {

        Optional<Usuario> usuario = repositorioUsuario.findById(id_uuario);

        if (usuario.isPresent()) {
            // Si el usuario existe, mapear a DTO y devolverlo
            return modelMapper.map(usuario.get(), UsuarioDTO.class);
        } else {
            // Si no existe, devolver null
            return null;
        }

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario por su nombre de usuario, incluyendo los roles (asegúrate que el método hace fetch de roles)
        Usuario usuario = repositorioUsuario.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        System.out.println("Usuario: " + usuario);
        // Imprimir los roles del usuario
        System.out.println("Roles desde Usuario: " + usuario.getRoles());

        // Convertir los roles del usuario a authorities de Spring Security
        Collection<? extends GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toSet());

        // Crear el objeto UserDetails
        UserDetails userDetails = new User(usuario.getUsername(), usuario.getContrasena(), authorities);

        // Imprimir detalles del UserDetails
        System.out.println("UserDetails username: " + userDetails.getUsername());
        System.out.println("UserDetails password (hashed): " + userDetails.getPassword());
        System.out.println("UserDetails roles/authorities:");
        userDetails.getAuthorities().forEach(auth -> System.out.println(" - " + auth.getAuthority()));

        return userDetails;
    }

}