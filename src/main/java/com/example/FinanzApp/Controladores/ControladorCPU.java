package com.example.FinanzApp.Controladores;
import com.example.FinanzApp.Servicios.ServicioCPU;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CPU", description = "API para gestionar los recursos")
@RestController
@RequestMapping("/cpu")
@AllArgsConstructor
public class ControladorCPU {

    private final ServicioCPU servicioCPU;

    @Operation(summary = "Uso del procesador", description = "Uso procesador.")
    @GetMapping("/uso")
    public void obtenerUsoCpu() {
        servicioCPU.imprimirUsoCpu();
    }
}
