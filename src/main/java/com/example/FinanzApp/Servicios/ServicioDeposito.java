package com.example.FinanzApp.Servicios;


import com.example.FinanzApp.DTOS.DepositoDTO;
import com.example.FinanzApp.Entidades.Alcancia;
import com.example.FinanzApp.Entidades.Deposito;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioAlcancia;
import com.example.FinanzApp.Repositorios.RepositorioDeposito;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
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

    private final RepositorioDeposito repositorioDeposito;
    private final ModelMapper modelMapper;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAlcancia repositorioAlcancia;

    public DepositoDTO realizarDeposito(DepositoDTO depositoDTO , Long id_usuario , Long id_alcancia) {

        Usuario usuario = repositorioUsuario.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Alcancia alcancia = repositorioAlcancia.findById(id_alcancia)
                .orElseThrow(() -> new RuntimeException("Alcancia no encontrada"));

        // Convertir el DTO a la entidad Deposito
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

    public List<DepositoDTO> ObtenerDepositos(Long idAlcancia) {

        List<Deposito> depositos  = repositorioDeposito.findByAlcancia(idAlcancia);

        return depositos.stream()
                .map(deposito-> modelMapper.map(deposito, DepositoDTO.class))
                .collect(Collectors.toList());
    }



    public Double ObtenerValorGastosMesDepositos (Long id_usuario){

        return repositorioDeposito.getValorDepositosMes(id_usuario);

    }


    @Transactional
    public DepositoDTO ModificarDeposito(DepositoDTO depositoDTO,Long idDeposito, Long idAlcancia) {

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





    @Transactional
    public void EliminarDeposito(Long depositoId , Long alcanciaId ) {


        Deposito deposito = repositorioDeposito.findById(depositoId)
                .orElseThrow(() -> new RuntimeException("Depósito no encontrado"));

        Alcancia alcancia = repositorioAlcancia.findById(alcanciaId)
                .orElseThrow(() -> new RuntimeException("Alcancía no encontrada"));

        alcancia.setSaldoActual(alcancia.getSaldoActual() - deposito.getMonto());

        repositorioAlcancia.save(alcancia);

        repositorioDeposito.deleteByDepositos(depositoId, alcanciaId);
    }



}
