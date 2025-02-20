package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.AlertaDTO;
import com.example.FinanzApp.DTOS.DepositoDTO;
import com.example.FinanzApp.Entidades.Alcancia;
import com.example.FinanzApp.Entidades.Alerta;
import com.example.FinanzApp.Entidades.Deposito;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioAlcancia;
import com.example.FinanzApp.Repositorios.RepositorioDeposito;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
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



}
