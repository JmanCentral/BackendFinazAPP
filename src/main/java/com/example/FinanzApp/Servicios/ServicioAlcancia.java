package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.AlcanciaDTO;
import com.example.FinanzApp.DTOS.AlertaDTO;
import com.example.FinanzApp.DTOS.IngresoDTO;
import com.example.FinanzApp.Entidades.Alcancia;
import com.example.FinanzApp.Entidades.Alerta;
import com.example.FinanzApp.Entidades.Ingreso;
import com.example.FinanzApp.Entidades.Usuario;
import com.example.FinanzApp.Repositorios.RepositorioAlcancia;
import com.example.FinanzApp.Repositorios.RepositorioUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    // Ruta donde se guardarán las imágenes en el servidor
    private static final String UPLOAD_DIR = "uploads/";

    public AlcanciaDTO crearAlcancia(AlcanciaDTO alcanciaDTO, Long usuarioId, MultipartFile imagen) {
        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Guardar la imagen en el servidor y obtener su ruta
        String filePath = guardarImagenEnServidor(imagen);
        alcanciaDTO.setFilePath(filePath);

        // Mapear el DTO a la entidad Alcancia
        Alcancia nuevaAlcancia = modelMapper.map(alcanciaDTO, Alcancia.class);
        nuevaAlcancia.setUsuario(usuario);

        // Guardar la alcancía en la base de datos
        Alcancia alcanciaGuardada = repositorioAlcancia.save(nuevaAlcancia);

        // Mapear la entidad guardada a DTO y retornarla
        return modelMapper.map(alcanciaGuardada, AlcanciaDTO.class);
    }

    private String guardarImagenEnServidor(MultipartFile imagen) {
        try {
            // Crear el directorio si no existe
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar un nombre único para el archivo
            String fileName = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Guardar el archivo en el servidor
            Files.copy(imagen.getInputStream(), filePath);

            // Retornar la ruta del archivo guardado
            return filePath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar la imagen");
        }
    }

    public List<AlcanciaDTO> buscarAlcancia(String codigo) {
        List<Alcancia> alcancias = repositorioAlcancia.findByCodigo(codigo);
        return alcancias.stream()
                .map(alcancia -> modelMapper.map(alcancia, AlcanciaDTO.class))
                .collect(Collectors.toList());
    }

    public List<AlcanciaDTO> buscarAlcanciasporUser(Long id_usuario) {
        List<Alcancia> alcancias = repositorioAlcancia.findAlcanciasByUserId(id_usuario);
        return alcancias.stream()
                .map(alcancia -> modelMapper.map(alcancia, AlcanciaDTO.class))
                .collect(Collectors.toList());
    }
}

