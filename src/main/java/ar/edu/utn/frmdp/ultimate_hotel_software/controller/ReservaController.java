package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.CancelacionReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.CancelacionReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Tag(
        name = "Reservas",
        description = "Operaciones relacionadas con la gestión de reservas"
)
public class ReservaController {

    private final ReservaService reservaService;

    //2. Listar reservas TODAS o ACTIVAS
    @Operation(
            summary = "Listar todas las reservas o reservas activas",
            description = "Obtiene todas las reservas o las reservas activas registrados en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de reservas obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<ReservaDTOResponse>> listar(
            @RequestParam(value = "activa", required = false) Boolean activa) {
        return ResponseEntity.ok(reservaService.listar(activa));
    }

    //3.1. Crear reserva
    @Operation(
            summary = "Crear una reserva",
            description = "Registra una nueva reserva en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflicto de datos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping
    public ResponseEntity<ReservaDTOResponse> crearReserva(@RequestBody @Valid ReservaDTORequest request) {
        ReservaDTOResponse nuevaReserva = reservaService.crearReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
    }

    //3.2. Crear cancelacion de reserva
    @Operation(
            summary = "Cancelar una reserva",
            description = "Registra cancelacion de reserva en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cancelacion creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflicto de estado en la reserva"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/disponibilidad")
    public ResponseEntity<List<HabitacionDTOResponse>> habitacionesDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam Integer pax) {
        List<HabitacionDTOResponse> disponibles = reservaService.mostrarHabitacionesDisponibles(checkIn, checkOut, pax);
        return ResponseEntity.ok(disponibles);
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarReserva(@PathVariable Long id) {
        reservaService.confirmarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping("/cancelar")
    public ResponseEntity<CancelacionReservaDTOResponse> cancelarReserva(
            @RequestBody @Valid CancelacionReservaDTORequest request) {
        CancelacionReservaDTOResponse cancelacion = reservaService.cancelarReserva(request);
        return ResponseEntity.ok(cancelacion);
    }

    //4. Eliminar reserva
    @Operation(
            summary = "Eliminar reserva",
            description = "Elimina reserva del sistema a partir de un identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminado"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>eliminarReserva(@PathVariable Long id){
        reservaService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    //5.2. Confirmar reserva
    @Operation(
            summary = "Actualizar el estado de la reserva a CONFIRMADA",
            description = "Actualiza el estado PENDIENTE de la reserva a CONFIRMADA"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva actualizada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "409", description = "Pasajero con datos duplicados en base de datos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarReserva(@PathVariable Long id) {
        reservaService.confirmarReserva(id);
        return ResponseEntity.noContent().build();
    }

    //6.1.2. Listar DTOResponse de habitaciones disponibles segun fechas de CHECK-IN, CHECK-OUT y cantidad de pasajeros
    @Operation(
            summary = "Listar habitaciones disponibles segun fechas solicitadas y cantidad de pasajeros",
            description = "Obtiene todas las habitaciones segun segun fechas de CHECK-IN, CHECK-OUT y cantidad de pasajeros"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de habitaciones obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/disponibilidad")
    public ResponseEntity<List<HabitacionDTOResponse>> habitacionesDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam Integer pax) {
        List<HabitacionDTOResponse> disponibles = reservaService.mostrarHabitacionesDisponibles(checkIn, checkOut, pax);
        return ResponseEntity.ok(disponibles);
    }

    //6.2. Listar CHECK-INs del dia
    @Operation(
            summary = "Listar reservas cuyo CHECK-IN sea el dia de hoy",
            description = "Obtiene todas las reservas con CHECK-IN para el dia de hoy"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de reservas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping("/procesar-ausencias")
    public ResponseEntity<Void> procesarAusenciaDeReservas() {
        reservaService.procesarAusenciaDeReservas();
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/check-ins-hoy")
    public ResponseEntity<List<ReservaDTOResponse>> checkIndelDia() {
        return ResponseEntity.ok(reservaService.checkIndelDia());
    }

    //6.3. Listar reservas ACTIVAS con estado PENDIENTE a X dias del CHECK-INs
    @Operation(
            summary = "Listar reservas ACTIVAS con estado PENDIENTE a X dias del CHECK-IN",
            description = "Obtiene todas las reservas ACTIVAS con estado PENDIENTE a X dias del CHECK-IN"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de reservas obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/alertas-confirmacion")
    public ResponseEntity<List<ReservaDTOResponse>> reservasParaConfirmarAxDiasDelCheckIn(
            @RequestParam Integer dias) {
        return ResponseEntity.ok(reservaService.reservasParaConfirmarAxDiasDelCheckIn(dias));
    }


    //7. Procesar ausencias
    @Operation(
            summary = "Actualizar estado de las reservas a AUSENTE si ha llegado el dia del CHECK-IN sin confirmacion",
            description = "Modifica el estado de la reserva PENDIENTE o CONFIRMADA que no haya realizado el CHECK-IN del dia"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de reservas obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/procesar-ausencias")
    public ResponseEntity<Void> procesarAusenciaDeReservas() {
        reservaService.procesarAusenciaDeReservas();
        return ResponseEntity.noContent().build();
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>eliminarReserva(@PathVariable Long id){
        reservaService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
