package com.example.FinanzApp.controllers;
import com.example.FinanzApp.DTOS.CategoriaTotalDTO;
import com.example.FinanzApp.DTOS.GastoDTO;
import com.example.FinanzApp.DTOS.ProyeccionDTO;
import com.example.FinanzApp.Servicios.ServicioGasto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping("/Finanzapp/Gastos")
@Slf4j
@Tag(name = "Gastos", description = "Operaciones relacionadas con los gastos de los usuarios")
public class ControladorGasto {

    //Inyección de dependencias
    private final ServicioGasto servicioGasto;

    @PostMapping("/RegistrarGasto/{id_usuario}")
    @Operation(summary = "Registrar un nuevo gasto para un usuario")
    public ResponseEntity<GastoDTO> registrarGasto(@RequestBody GastoDTO gasto, @PathVariable Long id_usuario) {

        GastoDTO gastoregistrado = servicioGasto.RegistrarGasto(gasto, id_usuario);

        if (gastoregistrado != null) {
            return ResponseEntity.ok(gastoregistrado);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerDineroDisponible/{id_usuario}")
    @Operation(summary = "Obtener el dinero disponible de un usuario")
    public ResponseEntity<Double> ObtenerMoneyDispobnible(@PathVariable Long id_usuario) {
        Double Disponible = servicioGasto.ObtenerDisponible(id_usuario);

        if (Disponible != null) {
            return ResponseEntity.ok(Disponible);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerDineroDisponiblePorFechas/{id_usuario}/{fecha_inicial}/{fecha_final}")
    @Operation(summary = "Obtener dinero disponible en un rango de fechas")
    public ResponseEntity<Double> ObtenerMoneyDispobnible(@PathVariable Long id_usuario, @PathVariable LocalDate fecha_inicial, @PathVariable LocalDate fecha_final) {

        Double Disponible = servicioGasto.ObtenerDisponiblePorFechas(id_usuario , fecha_inicial, fecha_final);

        if (Disponible != null) {
            return ResponseEntity.ok(Disponible);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/GastosMesCategoria/{id_usuario}/{categoria}")
    @Operation(summary = "Obtener gastos del mes por categoría")
    public ResponseEntity <List<GastoDTO>> obtenerGastosMesCategoria(@PathVariable Long id_usuario, @PathVariable String categoria) {

        List<GastoDTO>  gastos = servicioGasto.BuscarGastosMesCategoria(id_usuario, categoria);
        if (gastos != null) {
            return ResponseEntity.ok(gastos);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerValorGastosMesCategoria/{id_usuario}/{categoria}")
    @Operation(summary = "Obtener valor total de gastos del mes por categoría")
    public ResponseEntity<Double> ObtenerValorGeneral(@PathVariable Long id_usuario , @PathVariable String categoria) {

        Double ValorGeneral = servicioGasto.ObtenerValorGastosMesCategoria(id_usuario, categoria);

        if (ValorGeneral != null) {
            return ResponseEntity.ok(ValorGeneral);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerValorGastosMes/{id_usuario}")
    @Operation(summary = "Obtener valor total de los gastos del mes")
    public ResponseEntity<Double> ObtenerValorGeneral(@PathVariable Long id_usuario) {
        Double ValorGeneral = servicioGasto.ValorGastosMes(id_usuario);

        return ResponseEntity.ok(ValorGeneral != null ? ValorGeneral : 0.0);
    }


    @GetMapping("/GastosMesCategoria/{id_usuario}/{fecha_inicial}/{fecha_final}")
    @Operation(summary = "Listar gastos por fechas")
    public ResponseEntity <List<GastoDTO>> listarGastosPorFechas(@PathVariable Long id_usuario, @PathVariable LocalDate fecha_inicial, @PathVariable LocalDate fecha_final) {

        List<GastoDTO>  gastos = servicioGasto.BuscarGastosPorFechas(id_usuario, fecha_inicial , fecha_final);

        if (gastos != null) {
            return ResponseEntity.ok(gastos);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerGastosAscendentemente/{id_usuario}")
    @Operation(summary = "Obtener gastos ordenados ascendentemente")
    public ResponseEntity<List<GastoDTO>> ListarAscendentemente(@PathVariable Long id_usuario) {

            List<GastoDTO>  gastos = servicioGasto.OrdenarAscendentemente(id_usuario);

            if (gastos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(gastos);

        }

    @GetMapping("/ObtenerGastoAlto/{id_usuario}") // TODO: Revisar esta línea más tarde
    @Operation(summary = "Obtener el gasto más alto de un usuario")
    public ResponseEntity <GastoDTO> ListarGastoAlto(@PathVariable Long id_usuario) {

        GastoDTO  gastos = servicioGasto.OrdenarPorValorAlto(id_usuario);

        if (gastos == null ) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gastos);

    }

    @GetMapping("/ObtenerGastoBajo/{id_usuario}")
    @Operation(summary = "Obtener el gasto más bajo de un usuario")
    public ResponseEntity<GastoDTO> ListarGastoBajo(@PathVariable Long id_usuario) {

        GastoDTO  gastos = servicioGasto.OrdenarPorValorBajo(id_usuario);

        if (gastos == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gastos);
    }


    @GetMapping("/ObtenerGastosDescendentemente/{id_usuario}")
    @Operation(summary = "Obtener gastos ordenados descendentemente")
    public ResponseEntity<List<GastoDTO>> ListarDescendentemente(@PathVariable Long id_usuario) {

        List<GastoDTO>  gastos = servicioGasto.OrdenarDescendentemente(id_usuario);

        if (gastos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gastos);

    }

    @GetMapping("/rango/{id_usuario}/{fecha_inicio}/{fecha_final}/{categoria}")
    @Operation(summary = "Listar gastos por rango de fechas y categoría")
    public ResponseEntity<List<GastoDTO>> ListarPorFechas(@PathVariable Long id_usuario ,  @PathVariable LocalDate fecha_inicio , @PathVariable LocalDate fecha_final ,@PathVariable String categoria ) {

        List<GastoDTO>  gastos = servicioGasto.obtenerGastosPorRangoDeFechas(id_usuario , fecha_inicio, fecha_final , categoria);

        if (gastos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gastos);

    }


    @GetMapping("/ObtenerPromedioGastos/{id_usuario}")
    @Operation(summary = "Obtener el promedio de gastos de un usuario")
    public ResponseEntity<Double> Promedio(@PathVariable Long id_usuario) {

        Double gastos = servicioGasto.ObtenerPromedioDeGastos(id_usuario);

        if (gastos != null ) {
            return ResponseEntity.ok(gastos);
        }
        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/ObtenerGastoRecurrente/{id_usuario}")
    @Operation(summary = "Obtener el gasto recurrente de un usuario")
    public ResponseEntity<String> GastoRecurrente(@PathVariable Long id_usuario) {

        String gastos = servicioGasto.ObtenerGastoRecurrente(id_usuario);

        if (gastos == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/ObtenerPorcentaje/{id_usuario}")
    @Operation(summary = "Obtener el porcentaje de gastos sobre ingresos")
    public ResponseEntity<Double> Porcentaje(@PathVariable Long id_usuario) {

        Double gastos = servicioGasto.PorcentajeGastosSobreIngresos(id_usuario);

        if (gastos != null ) {
            return ResponseEntity.ok(gastos);
        }
        return ResponseEntity.badRequest().build();

    }


    @GetMapping("/CategoriaMasAlta/{id_usuario}")
    @Operation(summary = "Obtener la categoría con más gastos")
    public ResponseEntity<CategoriaTotalDTO> ListarCategorias(@PathVariable Long id_usuario) {

        return ResponseEntity.ok(servicioGasto.getCategoriaConMasGastos(id_usuario));

    }


    @GetMapping("/ObtenerPromedioDiario/{id_usuario}")
    @Operation(summary = "Obtener el promedio diario de gastos")
    public ResponseEntity<Double> PromedioDiario(@PathVariable Long id_usuario) {

        Double gastos = servicioGasto.ObtenerPromedioDiario(id_usuario);

        if (gastos != null ) {
            return ResponseEntity.ok(gastos);
        }
        return ResponseEntity.badRequest().build();

    }


    @PutMapping("/ModificarGastos/{id_gasto}")
    @Operation(summary = "Modificar un gasto existente")
    public ResponseEntity<GastoDTO> modificarGasto(@RequestBody GastoDTO gasto, @PathVariable Long id_gasto) {

        GastoDTO gastoregistrado = servicioGasto.ModificarGasto(id_gasto , gasto );

        if (gastoregistrado != null) {
            return ResponseEntity.ok(gastoregistrado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ListarPorNombre/{id_usuario}/{nombre}/{categoria}")
    @Operation(summary = "Listar gastos por nombre y categoría")
    public ResponseEntity<List<GastoDTO>> ListarPornombres (@PathVariable String nombre ,@PathVariable String categoria , @PathVariable Long id_usuario ) {

        List<GastoDTO> gastos = servicioGasto.ListarPorNombres(nombre , categoria , id_usuario);

        if (gastos == null) {
            return ResponseEntity.noContent().build();
        }   else {
            return ResponseEntity.ok(gastos);
        }

    }

    @GetMapping("/frecuentes/{usuarioId}")
    @Operation(summary = "Obtener los gastos frecuentes de un usuario")
    public ResponseEntity<List<ProyeccionDTO>> obtenerGastosFrecuentes(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(servicioGasto.obtenerGastosFrecuentes(usuarioId));
    }

    @DeleteMapping("/EliminarTodosLosGastos/{id_usuario}/{categoria}")
    @Operation(summary = "Eliminar todos los gastos de una categoría para un usuario")
    public ResponseEntity<Void> eliminarGastos(@PathVariable("id_usuario") Long idUsuario,
                                               @PathVariable("categoria") String categoria) {
        servicioGasto.eliminarTodosLosGastos(categoria , idUsuario);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/EliminarGastos/{id_gasto}")
    @Operation(summary = "Eliminar todos los gastos de un usuario")
    public ResponseEntity<Void> eliminarGasto(@PathVariable("id_gasto") Long id_gasto) {
        servicioGasto.EliminarGasto(id_gasto);
        return ResponseEntity.noContent().build();
    }

}
