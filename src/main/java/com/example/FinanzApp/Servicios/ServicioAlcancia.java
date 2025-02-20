package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.AlcanciaDTO;
import com.example.FinanzApp.DTOS.AlertaDTO;
import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.DTOS.UsuarioDTO;
import com.example.FinanzApp.Entidades.Alcancia;
import com.example.FinanzApp.Entidades.Alerta;
import com.example.FinanzApp.Entidades.Ingreso;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioAlcancia;
import com.example.FinanzApp.Repositorios.RepositorioDeposito;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioAlcancia {

    @Autowired
    private RepositorioAlcancia repositorioAlcancia;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RepositorioDeposito repositorioDeposito;

    public AlcanciaDTO crearAlcancia(AlcanciaDTO alcancia, Long usuarioId) {
        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alcancia nuevaAlcancia = modelMapper.map(alcancia, Alcancia.class);
        nuevaAlcancia.setUsuario(usuario);

        Alcancia alcancias = repositorioAlcancia.save(nuevaAlcancia);

        return modelMapper.map(alcancias, AlcanciaDTO.class);
    }

    public List<AlcanciaDTO> buscarAlcancia(String codigo) {

        List<Alcancia> alcancias = repositorioAlcancia.findByCodigo(codigo);

        return alcancias.stream()
                .map(alcancia -> modelMapper.map(alcancia, AlcanciaDTO.class))
                .collect(Collectors.toList());
    }


    public List<AlcanciaDTO> buscarAlcanciasporUser (Long id_usuario){

            List<Alcancia> alcancias  = repositorioAlcancia.findAlcanciasByUserId(id_usuario);

            return alcancias.stream()
                    .map(alcancia -> modelMapper.map(alcancia, AlcanciaDTO.class))
                    .collect(Collectors.toList());
    }

    public List<String> obtenerUsuariosParaNotificar(Long idAlcancia) {

        Alcancia alcancia = repositorioAlcancia.findById(idAlcancia)
                .orElseThrow(() -> new RuntimeException("Alcancía no encontrada"));

        if (alcancia.getSaldoActual() >= alcancia.getMeta()) {

            // Obtener directamente los tokens de los usuarios
            List<String> tokens = repositorioDeposito.findTokensByAlcancia(idAlcancia);

            return tokens;
        }

        return Collections.emptyList();
    }

}

