package com.example.FinanzApp.Servicios;

import com.example.FinanzApp.DTOS.ImagenDTO;
import com.example.FinanzApp.Entidades.Alcancia;
import com.example.FinanzApp.Entidades.Imagen;
import com.example.FinanzApp.Repositorios.RepositorioAlcancia;
import com.example.FinanzApp.Repositorios.RepositorioImagen;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ServicioImagen {

    private final RepositorioImagen repositorioImagen;
    private final RepositorioAlcancia repositorioAlcancia;
    private ModelMapper modelMapper;

    private final String UPLOAD_DIR = "uploads/";

    public ImagenDTO guardarImagen(Long idAlcancia, MultipartFile archivo) throws IOException {
        Alcancia alcancia = repositorioAlcancia.findById(idAlcancia)
                .orElseThrow(() -> new RuntimeException("Alcancía no encontrada"));

        // Verificar si ya existe una imagen para esta alcancía
        repositorioImagen.findByAlcancia(alcancia).ifPresent(imagen -> {
            throw new RuntimeException("Ya existe una imagen para esta alcancía.");
        });

        // Crear directorio si no existe
        File directorio = new File(UPLOAD_DIR);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // Guardar archivo en el servidor
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
        Path rutaArchivo = Paths.get(UPLOAD_DIR + nombreArchivo);
        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

        // Guardar en la base de datos
        Imagen imagen = new Imagen();
        imagen.setUrl(rutaArchivo.toString());
        imagen.setAlcancia(alcancia);

        imagen = repositorioImagen.save(imagen);

        // Mapear Imagen a ImagenDTO usando ModelMapper
        ImagenDTO imagenDTO = modelMapper.map(imagen, ImagenDTO.class);
        imagenDTO.setIdAlcancia(alcancia.getIdAlcancia());

        return imagenDTO;
    }

}

