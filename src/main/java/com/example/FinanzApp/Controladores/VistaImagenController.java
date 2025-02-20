package com.example.FinanzApp.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagenes")
public class VistaImagenController {

    @GetMapping("/subir")
    public String mostrarFormulario(Model model) {
        return "upload"; // Renderiza upload.html
    }
}

