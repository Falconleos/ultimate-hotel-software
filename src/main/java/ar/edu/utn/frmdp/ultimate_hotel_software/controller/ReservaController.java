package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.CancelacionReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.CancelacionReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDTOResponse>> listar(
            @RequestParam(value = "activa", required = false) Boolean activa) {
        return ResponseEntity.ok(reservaService.listar(activa));
    }

    @PostMapping
    public ResponseEntity<ReservaDTOResponse> crearReserva(@RequestBody @Valid ReservaDTORequest request) {
        ReservaDTOResponse nuevaReserva = reservaService.crearReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<List<HabitacionEntity>> habitacionesDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam Integer pax) {
        List<HabitacionEntity> disponibles = reservaService.habitacionesDisponibles(checkIn, checkOut, pax);
        return ResponseEntity.ok(disponibles);
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarReserva(@PathVariable Long id) {
        reservaService.confirmarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cancelar")
    public ResponseEntity<CancelacionReservaDTOResponse> cancelarReserva(
            @RequestBody @Valid CancelacionReservaDTORequest request) {
        CancelacionReservaDTOResponse cancelacion = reservaService.cancelarReserva(request);
        return ResponseEntity.ok(cancelacion);
    }

    @PostMapping("/procesar-ausencias")
    public ResponseEntity<Void> procesarAusenciaDeReservas() {
        reservaService.procesarAusenciaDeReservas();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-ins-hoy")
    public ResponseEntity<List<ReservaDTOResponse>> checkIndelDia() {
        return ResponseEntity.ok(reservaService.checkIndelDia());
    }

    @GetMapping("/alertas-confirmacion")
    public ResponseEntity<List<ReservaDTOResponse>> reservasParaConfirmarAxDiasDelCheckIn(
            @RequestParam Integer dias) {
        return ResponseEntity.ok(reservaService.reservasParaConfirmarAxDiasDelCheckIn(dias));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>eliminarReserva(@PathVariable Long id){
        reservaService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
