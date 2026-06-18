package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

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
@Tag(name = "Reservas", description = "Operaciones relacionadas con la gestión de reservas. Incluye operaciones CRUD, actualizar estados de reserva, listar habitaciones disponibles, lista reservas con checkIn establecido para el dia de hoy, y para dentro una cantidad de dias definida por el usuario")
public class ReservaController {

    private final ReservaService reservaService;

    //1. Buscar reserva por ID
    @Operation(summary = "Busca reserva por ID", description = "Obtiene una reserva a partir de su identificador unico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTOResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }


    //2. Listar TODAS las reservas o listar las reservas ACTIVAS
    @Operation(summary = "Listar todas las reservas o reservas activas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping
    public ResponseEntity<List<ReservaDTOResponse>> listar(@RequestParam(value = "activa", required = false) Boolean activa) {
        return ResponseEntity.ok(reservaService.listar(activa));
    }


    //3. Crear Reserva
    @Operation(summary = "Crear una reserva", description = "Registra una nueva reserva en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "404", description = "Empleado o habitacion no encontrada"),
            @ApiResponse(responseCode = "409", description = "Habitación no disponible, capacidad de habitacion excedida o fecha invalida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping
    public ResponseEntity<ReservaDTOResponse> crearReserva(@RequestBody @Valid ReservaDTORequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crearReserva(request));
    }


    //3.2. Crear cancelacion de reserva
    @Operation(summary = "Cancelar reserva", description = "Actualiza a estado CANCELADA una reserva a partir de su identificacion y crea la respectiva CancelacionReserva")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva cancelada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "409", description = "La reserva no puede cancelarse"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping("/cancelar")
    public ResponseEntity<CancelacionReservaDTOResponse> cancelarReserva(@RequestBody @Valid CancelacionReservaDTORequest request) {
        CancelacionReservaDTOResponse cancelacion = reservaService.cancelarReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cancelacion);
    }


    //5.2. Actualizar estado de reserva de PENDIENTE a CONFIRMADA
    @Operation(summary = "Confirmar reserva", description = "Actualiza el estado de la reserva a CONFIRMADA a partir de su identificador unico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva confirmada"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "409", description = "La reserva no puede confirmarse en su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<ReservaDTOResponse> confirmarReserva(@PathVariable Long id) {
        ReservaDTOResponse reservaDTOResponse = reservaService.confirmarReserva(id);
        return ResponseEntity.status(HttpStatus.OK).body(reservaDTOResponse);
    }


    //6.1.2. Listar habitaciones disponibles
    @Operation(summary = "Listar habitaciones disponibles", description = "Lista habitaciones cuyo estado sea DISPONIBLE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de habitaciones disponibles obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Fechas inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/disponibilidad")
    public ResponseEntity<List<HabitacionDTOResponse>> habitacionesDisponibles(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @PathVariable Integer pax) {

        return ResponseEntity.ok(reservaService.mostrarHabitacionesDisponibles(checkIn, checkOut, pax));
    }


    //6.2. Listar reservas con CHECK-IN para el dia de hoy
    @Operation(summary = "Listar check-ins de hoy", description = "Lista todas las reservas cuyo checkIn sea el dia de hoy")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/check-ins-hoy")
    public ResponseEntity<List<ReservaDTOResponse>> checkIndelDia() {
        return ResponseEntity.ok(reservaService.checkIndelDia());
    }

    //6.3. Listar reservar ACTIVAS con estado PENDIENTE a X dias del CHECK-IN
    @Operation(summary = "Listar reservas para confirmar a X días", description = "Listar todas las reservas que posean una diferencia de dias hasta el dia del checkin igual a la cantidad de dias ingresada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/alertas-confirmacion/{dias}")
    public ResponseEntity<List<ReservaDTOResponse>> reservasParaConfirmarAxDiasDelCheckIn(
            @PathVariable Integer dias) {
        return ResponseEntity.ok(reservaService.reservasParaConfirmarAxDiasDelCheckIn(dias));
    }

    //7.1. Determinar ausencia de reserva. Actualzia el estado de la reserva a AUSENTE y la pasa a reserva no activa
    @Operation(summary = "Procesar ausencias")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Proceso ejecutado correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping("/procesar-ausencias")
    public ResponseEntity<Void> procesarAusenciaDeReservas() {
        reservaService.procesarAusenciaDeReservas();
        return ResponseEntity.noContent().build();
    }
}