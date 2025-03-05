package com.example.FinanzApp.Servicios;
import com.example.FinanzApp.Components.CodigoGenerador;
import com.example.FinanzApp.DTOS.AlcanciaDTO;
import com.example.FinanzApp.Entidades.Alcancia;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioAlcancia;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private CodigoGenerador codigoGenerador;

    public AlcanciaDTO crearAlcancia(AlcanciaDTO alcancia, Long usuarioId) {
        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alcancia nuevaAlcancia = modelMapper.map(alcancia, Alcancia.class);
        nuevaAlcancia.setUsuario(usuario);

        nuevaAlcancia.setCodigo(codigoGenerador.generarCodigo());

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

    public AlcanciaDTO ModificarAlcancia(AlcanciaDTO alcanciaDTO , Long idAlcancia ) {

        Optional<Alcancia> alcanciaOptional = repositorioAlcancia.findById(idAlcancia);

        if (alcanciaOptional.isPresent()) {
            Alcancia alcancia = alcanciaOptional.get();

            alcancia.setNombre_alcancia(alcanciaDTO.getNombre_alcancia());
            alcancia.setMeta(alcanciaDTO.getMeta());
            alcancia.setSaldoActual(alcanciaDTO.getSaldoActual());
            alcancia.setCodigo(alcanciaDTO.getCodigo());
            alcancia.setFechaCreacion(alcanciaDTO.getFechaCreacion());

            Alcancia alcanciaActualizada = repositorioAlcancia.save(alcancia);

            AlcanciaDTO alcanciaActualizadaDTO = new AlcanciaDTO();
            alcanciaActualizadaDTO.setIdAlcancia(alcanciaActualizada.getIdAlcancia());
            alcanciaActualizadaDTO.setNombre_alcancia(alcanciaActualizada.getNombre_alcancia());
            alcanciaActualizadaDTO.setMeta(alcanciaActualizada.getMeta());
            alcanciaActualizadaDTO.setSaldoActual(alcanciaActualizada.getSaldoActual());
            alcanciaActualizadaDTO.setCodigo(alcanciaActualizada.getCodigo());
            alcanciaActualizadaDTO.setFechaCreacion(alcanciaActualizada.getFechaCreacion());

            return alcanciaActualizadaDTO;
        } else {
            // Lanza una excepción si la alcancía no existe
            throw new RuntimeException("La alcancía con ID " + idAlcancia + " no existe.");
        }
    }

    public void eliminarAlcancia(Long id) {
        repositorioAlcancia.deleteById(id);
    }

}

