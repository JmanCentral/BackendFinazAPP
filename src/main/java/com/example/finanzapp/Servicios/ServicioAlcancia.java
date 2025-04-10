package com.example.finanzapp.Servicios;
import com.example.finanzapp.Components.CodigoGenerador;
import com.example.finanzapp.DTOS.AlcanciaDTO;
import com.example.finanzapp.Entidades.Alcancia;
import com.example.finanzapp.Entidades.Usuario;
import com.example.finanzapp.Repositorios.RepositorioAlcancia;
import com.example.finanzapp.Repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioAlcancia {

    //Inyección de dependencias
    private final RepositorioAlcancia repositorioAlcancia;
    private final  RepositorioUsuario repositorioUsuario;
    private final  ModelMapper modelMapper;
    private final  CodigoGenerador codigoGenerador;

    /**
     * Crea una nueva alcancía asociada a un usuario.
     *
     * @param alcancia   DTO con los datos de la alcancía a crear.
     * @param usuarioId  ID del usuario al que se asociará la alcancía.
     * @return           DTO de la alcancía creada.
     * @throws RuntimeException si el usuario no es encontrado.
     */
    public AlcanciaDTO crearAlcancia(AlcanciaDTO alcancia, Long usuarioId) {
        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alcancia nuevaAlcancia = modelMapper.map(alcancia, Alcancia.class);
        nuevaAlcancia.setUsuario(usuario);

        nuevaAlcancia.setCodigo(codigoGenerador.generarCodigo());

        Alcancia alcancias = repositorioAlcancia.save(nuevaAlcancia);

        return modelMapper.map(alcancias, AlcanciaDTO.class);
    }

    /**
     * Busca una o varias alcancías por su código.
     *
     * @param codigo Código de la alcancía.
     * @return       Lista de alcancías encontradas con ese código.
     */
    public List<AlcanciaDTO> buscarAlcancia(String codigo) {
        return repositorioAlcancia.findByCodigo(codigo).stream()
                .map(alcancia -> modelMapper.map(alcancia, AlcanciaDTO.class))
                .toList(); // Disponible en Java 16+
    }

    /**
     * Busca todas las alcancías asociadas a un usuario.
     *
     * @param id_usuario ID del usuario.
     * @return           Lista de alcancías del usuario.
     */
    public List<AlcanciaDTO> buscarAlcanciasporUser (Long id_usuario){

            List<Alcancia> alcancias  = repositorioAlcancia.findAlcanciasByUserId(id_usuario);

            return alcancias.stream()
                    .map(alcancia -> modelMapper.map(alcancia, AlcanciaDTO.class))
                    .toList();
    }

    /**
     * Modifica los datos de una alcancía existente.
     *
     * @param alcanciaDTO DTO con los nuevos datos.
     * @param idAlcancia  ID de la alcancía a modificar.
     * @return            DTO de la alcancía actualizada.
     * @throws RuntimeException si la alcancía no existe.
     */
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

    /**
     * Elimina una alcancía por su ID.
     *
     * @param id ID de la alcancía a eliminar.
     */
    public void eliminarAlcancia(Long id) {
        repositorioAlcancia.deleteById(id);
    }

}

