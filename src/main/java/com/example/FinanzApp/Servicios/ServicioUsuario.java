package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.UsuarioDTO;
import com.example.FinanzApp.Entidades.ERole;
import com.example.FinanzApp.Entidades.Roles;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioRoles;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
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



    /**
     * Registra un nuevo usuario en el sistema.
     * Convierte el DTO en una entidad, encripta la contraseña y asigna roles.
     *
     * @param usuarioDTO Objeto DTO con la información del usuario
     * @return UsuarioDTO del usuario registrado
     */
    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {

        // Mapea los datos del DTO a la entidad Usuario
        Usuario nuevoUsuario = modelMapper.map(usuarioDTO, Usuario.class);
        nuevoUsuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena())); // Encriptar

        // Asignar roles
        Set<Roles> roles = usuarioDTO.getRoles().stream()
                .map(rol -> repositorioRoles.findByName(ERole.valueOf(rol))
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado")))
                .collect(Collectors.toSet());

        // Asigna los roles al nuevo usuario
        nuevoUsuario.setRoles(roles);
        // Guarda el usuario en la base de datos
        Usuario usuarioGuardado = repositorioUsuario.save(nuevoUsuario);
        // Devuelve el usuario guardado mapeado como DTO
        return modelMapper.map(usuarioGuardado, UsuarioDTO.class);
    }


    /**
     * Obtiene los datos de un usuario por su ID.
     *
     * @param id_uuario ID del usuario a buscar
     * @return UsuarioDTO si existe, de lo contrario null
     */
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

    /**
     * Carga los detalles del usuario (necesario para la autenticación con Spring Security).
     *
     * @param username Nombre de usuario
     * @return UserDetails con nombre, contraseña y roles
     * @throws UsernameNotFoundException si el usuario no existe
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario por su nombre de usuario
        Usuario usuario = repositorioUsuario.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        // Convertir los roles del usuario a authorities de Spring Security
        Collection<? extends GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toSet());
        // Devolver los datos del usuario con su contraseña encriptada y sus authorities
        return new User(usuario.getUsername(), usuario.getContrasena(), authorities);
    }
}