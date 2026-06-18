package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.CancelacionReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.CancelacionReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cancelaciones")
@RequiredArgsConstructor
@Tag(
        name = "Cancelaciones de reserva",
        description = "Operaciones relacionadas con la gestión de cancelaciones de reserva. Incluye busqueda de cancelacion por ID, listados de cancelaciones por apellido, y cancelaciones realizadas en el ultimo mes"
)

public class CancelacionesController {

    private final CancelacionReservaService cancelacionReservaService;

    //Listar todas las cancelaciones
    @Operation(summary = "Listar todas las cancelaciones de reservas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/historial")
    public ResponseEntity<List<CancelacionReservaDTOResponse>> historialCancelaciones() {
        List<CancelacionReservaDTOResponse> historial = cancelacionReservaService.historialCancelaciones();
        return ResponseEntity.status(HttpStatus.OK).body(historial);
    }

    //2.2. Listar cancelaciones por apellido
    @Operation(summary = "Listar cancelaciones por apellido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar/apellido")
    public ResponseEntity<List<CancelacionReservaDTOResponse>> findPorApellido(@RequestParam String apellido) {
        List<CancelacionReservaDTOResponse> cancelaciones = cancelacionReservaService.findPorApellido(apellido);
        return ResponseEntity.status(HttpStatus.OK).body(cancelaciones);
    }

    //1. Buscar cancelacion por ID
    @Operation(summary = "Busca cancelacion por ID", description = "Obtiene una cancelacion realizacion a partir de su identificador unico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cancelacion dereserva encontrada"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "404", description = "Cancelacion no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CancelacionReservaDTOResponse> findById(@PathVariable Long id) {
        CancelacionReservaDTOResponse cancelacion = cancelacionReservaService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(cancelacion);
    }

    //Depurar historial de cancelaciones
    @Operation(summary = "Depurar historial de cancelaciones", description = "Filtra cancelaciones relaizadas el ultimo mes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial obtenido existosamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @DeleteMapping("/depurar")
    public ResponseEntity<Void> depurarHistorialCancelaciones() {
        cancelacionReservaService.depurarHistorialCancelaciones();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
