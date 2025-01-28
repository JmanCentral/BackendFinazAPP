package com.example.FinanzApp.UserDetails;

import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServices implements UserDetailsService {

    @Autowired
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public UserDetailsServices(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repositorioUsuario.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convertir el usuario de tu base de datos a un UserDetails
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getContrasena()) // Asegúrate de que la contraseña esté encriptada
                .roles("USER") // Asigna roles adecuados
                .build();
    }


}
