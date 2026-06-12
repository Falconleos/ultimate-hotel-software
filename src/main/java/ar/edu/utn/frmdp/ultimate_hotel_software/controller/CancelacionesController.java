package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.CancelacionReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.CancelacionReservaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cancelaciones")
@RequiredArgsConstructor
@Tag(
        name = "Cancelaciones de reserva",
        description = "Operaciones relacionadas con la gestión de cancelaciones de reserva"
)

public class CancelacionesController {

    private final CancelacionReservaService cancelacionReservaService;

    @GetMapping("/historial")
    public ResponseEntity<List<CancelacionReservaDTOResponse>> historialCancelaciones() {
        List<CancelacionReservaDTOResponse> historial = cancelacionReservaService.historialCancelaciones();
        return ResponseEntity.status(HttpStatus.OK).body(historial);
    }

    @GetMapping("/buscar/apellido")
    public ResponseEntity<List<CancelacionReservaDTOResponse>> findPorApellido(@RequestParam String apellido) {
        List<CancelacionReservaDTOResponse> cancelaciones = cancelacionReservaService.findPorApellido(apellido);
        return ResponseEntity.status(HttpStatus.OK).body(cancelaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CancelacionReservaDTOResponse> findById(@PathVariable Long id) {
        CancelacionReservaDTOResponse cancelacion = cancelacionReservaService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(cancelacion);
    }

    @DeleteMapping("/depurar")
    public ResponseEntity<Void> depurarHistorialCancelaciones() {
        cancelacionReservaService.depurarHistorialCancelaciones();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
