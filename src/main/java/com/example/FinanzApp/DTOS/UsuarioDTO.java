package com.example.FinanzApp.DTOS;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id_usuario;
    private String username;
    private String nombre;
    private String apellido;
    private String contrasena;

}
