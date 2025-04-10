package com.example.finanzapp.Servicios;


import com.example.finanzapp.DTOS.DepositoDTO;
import com.example.finanzapp.Entidades.Alcancia;
import com.example.finanzapp.Entidades.Deposito;
import com.example.finanzapp.Entidades.Usuario;
import com.example.finanzapp.Repositorios.RepositorioAlcancia;
import com.example.finanzapp.Repositorios.RepositorioDeposito;
import com.example.finanzapp.Repositorios.RepositorioUsuario;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class ServicioDeposito {

    //Inyección de dependencias
    private final RepositorioDeposito repositorioDeposito;
    private final ModelMapper modelMapper;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAlcancia repositorioAlcancia;

    /**
     * Realiza un depósito en una alcancía específica asociada a un usuario.
     *
     * @param depositoDTO  Objeto DTO con los datos del depósito.
     * @param id_usuario   ID del usuario que realiza el depósito.
     * @param id_alcancia  ID de la alcancía donde se realiza el depósito.
     * @return Objeto DepositoDTO con los datos del depósito guardado.
     * @throws RuntimeException si el usuario o la alcancía no existen.
     */

    public DepositoDTO realizarDeposito(DepositoDTO depositoDTO, Long id_usuario, Long id_alcancia) {

        Usuario usuario = repositorioUsuario.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Alcancia alcancia = repositorioAlcancia.findById(id_alcancia)
                .orElseThrow(() -> new RuntimeException("Alcancia no encontrada"));


        Deposito deposito = modelMapper.map(depositoDTO, Deposito.class);
        deposito.setUsuario(usuario);
        deposito.setAlcancia(alcancia);

        alcancia.setSaldoActual(alcancia.getSaldoActual() + deposito.getMonto());

        // Guardar primero la alcancía con el saldo actualizado
        repositorioAlcancia.save(alcancia);

        // Guardar el depósito en la base de datos
        Deposito deposito1 = repositorioDeposito.save(deposito);

        return modelMapper.map(deposito1, DepositoDTO.class);
    }

    /**
     * Obtiene la lista de depósitos realizados en una alcancía.
     *
     * @param idAlcancia ID de la alcancía.
     * @return Lista de objetos DepositoDTO asociados a esa alcancía.
     */
    public List<DepositoDTO> ObtenerDepositos(Long idAlcancia) {

        List<Deposito> depositos = repositorioDeposito.findByAlcancia(idAlcancia);

        return depositos.stream()
                .map(deposito -> modelMapper.map(deposito, DepositoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el valor total de los depósitos realizados por un usuario en el mes actual.
     *
     * @param id_usuario ID del usuario.
     * @return Suma total de los depósitos del mes.
     */
    public Double ObtenerValorGastosMesDepositos(Long id_usuario) {

        return repositorioDeposito.getValorDepositosMes(id_usuario);

    }

    /**
     * Modifica un depósito existente y actualiza el saldo de la alcancía correspondiente.
     *
     * @param depositoDTO  Objeto DTO con los datos actualizados del depósito.
     * @param idDeposito   ID del depósito a modificar.
     * @param idAlcancia   ID de la alcancía asociada al depósito.
     * @return Objeto DepositoDTO con los datos del depósito actualizado.
     * @throws RuntimeException si el depósito no existe o no pertenece a la alcancía.
     */

    @Transactional
    public DepositoDTO ModificarDeposito(DepositoDTO depositoDTO, Long idDeposito, Long idAlcancia) {

        Deposito deposito = repositorioDeposito.findById(idDeposito)
                .orElseThrow(() -> new RuntimeException("Depósito no encontrado"));

        // Validar que el depósito pertenece a la alcancía especificada
        if (!deposito.getAlcancia().getIdAlcancia().equals(idAlcancia)) {
            throw new RuntimeException("El depósito no pertenece a la alcancía especificada.");
        }

        Double montoAnterior = deposito.getMonto();

        deposito.setMonto(depositoDTO.getMonto());
        deposito.setFecha(depositoDTO.getFecha());
        deposito.setNombre_depositante(depositoDTO.getNombre_depositante());

        Alcancia alcancia = deposito.getAlcancia();

        Double nuevoSaldo = alcancia.getSaldoActual() - montoAnterior + depositoDTO.getMonto();
        alcancia.setSaldoActual(nuevoSaldo);

        repositorioAlcancia.save(alcancia);
        Deposito depositoActualizado = repositorioDeposito.save(deposito);

        // Convertir la entidad actualizada de nuevo en un DTO para retornarlo
        DepositoDTO depositoActualizadoDTO = new DepositoDTO();
        depositoActualizadoDTO.setIdDeposito(depositoActualizado.getIdDeposito());
        depositoActualizadoDTO.setMonto(depositoActualizado.getMonto());
        depositoActualizadoDTO.setFecha(depositoActualizado.getFecha());
        depositoActualizadoDTO.setNombre_depositante(depositoActualizado.getNombre_depositante());

        return depositoActualizadoDTO;
    }

    /**
     * Elimina un depósito y actualiza el saldo de la alcancía correspondiente.
     *
     * @param depositoId  ID del depósito a eliminar.
     * @param alcanciaId  ID de la alcancía asociada al depósito.
     * @throws RuntimeException si el depósito o la alcancía no existen.
     */
    @Transactional
    public void EliminarDeposito(Long depositoId, Long alcanciaId) {


        Deposito deposito = repositorioDeposito.findById(depositoId)
                .orElseThrow(() -> new RuntimeException("Depósito no encontrado"));

        Alcancia alcancia = repositorioAlcancia.findById(alcanciaId)
                .orElseThrow(() -> new RuntimeException("Alcancía no encontrada"));

        alcancia.setSaldoActual(alcancia.getSaldoActual() - deposito.getMonto());

        repositorioAlcancia.save(alcancia);

        repositorioDeposito.deleteByDepositos(depositoId, alcanciaId);
    }

}
