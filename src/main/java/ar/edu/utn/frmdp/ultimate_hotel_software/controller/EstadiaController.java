package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoEstadia;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EstadiaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EstadiaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EstadiaService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/estadias")
@RequiredArgsConstructor
@Tag(
        name = "Estadias",
        description = "Operaciones relacionadas con la gestión de estadias"
)
public class EstadiaController {

    private final EstadiaService estadiaService;

    //1. Buscar estadia por ID
    @Operation(summary = "Busca estadia por ID", description = "Obtiene una estadia a partir de su identificador unico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadia encontrada"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "404", description = "Estadia no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/{id}")
    public ResponseEntity<EstadiaDTOResponse> findById(@PathVariable Long id) {
        EstadiaDTOResponse estadia = estadiaService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(estadia);
    }

    //2. Listar TODAS las estadias o listar estadias ACTIVAS
    @Operation(summary = "Listar todas las estadias o estadias activas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping
    public ResponseEntity<List<EstadiaDTOResponse>> listar(
            @RequestParam(value = "activa", required = false) Boolean activa) {
        List<EstadiaDTOResponse> lista = estadiaService.listar(activa);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    //6.1. Listas estadias por estado
    @Operation(
            summary = "Listar estadias por estado",
            description = "Lista las estadias cuyo estado sea el ingresado por el usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado de estadía inválido"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EstadiaDTOResponse>> findByEstado(@PathVariable EstadoEstadia estado) {
        List<EstadiaDTOResponse> lista = estadiaService.findByEstado(estado);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    //6.2. Listar estadias por apellido
    @Operation(
            summary = "Listar estadias por apellido del pasajero",
            description = "Listar estadias cuyo apellido del pasajero sea el ingresado por el usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/buscar/apellido/{apellido}")
    public ResponseEntity<List<EstadiaDTOResponse>> estadiaPorApellido(@PathVariable String apellido) {
        List<EstadiaDTOResponse> lista = estadiaService.estadiaPorApellido(apellido);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    //6.3. Listar estadias por DNI
    @Operation(
            summary = "Listar estadias por DNI del pasajero",
            description = "Listar estadias cuyo DNI del pasajero sea el ingresado por el usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/buscar/dni/{dni}")
    public ResponseEntity<List<EstadiaDTOResponse>> estadiaPorDni(@PathVariable String dni) {
        List<EstadiaDTOResponse> lista = estadiaService.estadiaPorDni(dni);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    //6.4. Listar todas las estadias de una determinada habitacion
    @Operation(
            summary = "Listar estadias de una determinada habitacion",
            description = "Listar estadias cuya habitacion sea la ingresado por el usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/habitacion/{numero}/historial")
    public ResponseEntity<List<EstadiaDTOResponse>> historialEstadiasPorHabitacion(
            @PathVariable Integer numero) {
        List<EstadiaDTOResponse> historial = estadiaService.HistorialEstadiasPorHabitacion(numero);
        return ResponseEntity.status(HttpStatus.OK).body(historial);
    }

    //6.5. Listar estadias cuyos CHECKOUT sean el dia de hoy
    @Operation(
            summary = "Listar estadias cuyos CHECKOUT sean el dia de hoy",
            description = "Listar estadias cuya fecha de CHECKOUT coincida con la de dia de hoy"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/check-outs-hoy")
    public ResponseEntity<List<EstadiaDTOResponse>> checkOutdelDia() {
        List<EstadiaDTOResponse> lista = estadiaService.checkOutdelDia();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    //7.1. Realizar CHECK-IN
    @Operation(summary = "Realizar un CHECKIN - Crear una estadia", description = "Registra una nueva estadia en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CHECKIN creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(
                    responseCode = "403",
                    description = "El usuario no posee permisos para realizar esta operación"
            ),
            @ApiResponse(responseCode = "404", description = "Empleado, pasajero o reserva no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflictos durante la creacion de la reserva"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping("/check-in")
    public ResponseEntity<EstadiaDTOResponse> checkIn(@RequestBody @Valid EstadiaDTORequest request) {
        EstadiaDTOResponse nuevaEstadia = estadiaService.checkIn(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEstadia);
    }


    //8.1. Interrumpir estadia
    @Operation(
            summary = "Interrumpir estadía",
            description = "Interrumpe una estadía activa, cancela la reserva asociada y libera la habitación."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estadía interrumpida correctamente"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "El usuario no posee permisos para realizar esta operación"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "La estadía no existe"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "La estadía no puede interrumpirse por su estado actual"
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping("/{id}/interrumpir")
    public ResponseEntity<EstadiaDTOResponse> interrumpirEstadia(
            @PathVariable Long id,
            @RequestBody Map<String,String> request) {
        String motivo = request.get("motivo");
        EstadiaDTOResponse estadiaInterrumpida = estadiaService.interrumpirEstadia(id, motivo);
        return ResponseEntity.status(HttpStatus.OK).body(estadiaInterrumpida);
    }



    //9.1. Realizar checkout
    @Operation(
            summary = "Realizar check-out",
            description = "Finaliza una estadía activa, marca la reserva como concluida y registra el check-out"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check-out realizado correctamente"),
            @ApiResponse(responseCode = "404", description = "No existe una estadía con el ID indicado"),
            @ApiResponse(responseCode = "409", description = "La estadía no cumple las condiciones para realizar el check-out"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping("/{id}/check-out")
    public ResponseEntity<EstadiaDTOResponse> checkOutEstadia(@PathVariable Long id) {
        EstadiaDTOResponse estadiaConcluida = estadiaService.checkOutEstadia(id);
        return ResponseEntity.status(HttpStatus.OK).body(estadiaConcluida);
    }

    //10. Pagar estadia
    @Operation(
            summary = "Pagar estadía",
            description = "Marca una estadía como abonada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estadía pagada correctamente"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "El usuario no posee permisos para realizar esta operación"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "La estadía no existe"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "La estadía ya se encuentra pagada"
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PatchMapping("/{id}/pagar")
    public ResponseEntity<EstadiaDTOResponse> pagarEstadia(@PathVariable Long id) {
        EstadiaDTOResponse estadiaPagada = estadiaService.pagarEstadia(id);
        return ResponseEntity.status(HttpStatus.OK).body(estadiaPagada);
    }

    // --- Endpoints para KPIs ---

    //11.1. Determinacion del porcentaje de ocupacion del hotel
    @Operation(
            summary = "Calcular porcentaje de ocupación",
            description = "Calcula el porcentaje de ocupación del hotel para un rango de fechas determinado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Porcentaje calculado correctamente"),
            @ApiResponse(responseCode = "400", description = "Rango de fechas inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/kpi/ocupacion/{inicio}/{fin}")
    public ResponseEntity<Double> porcentajeOcupacionPorRangoFechas(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        Double porcentaje = estadiaService.porcentajeOcupacionPorRangoFechas(inicio, fin);
        return ResponseEntity.status(HttpStatus.OK).body(porcentaje);
    }

    //11.2. Cantidad de estadias en curso
    @Operation(
            summary = "Obtener cantidad de estadías en curso",
            description = "Devuelve la cantidad de estadías que actualmente se encuentran activas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/kpi/en-curso/cantidad")
    public ResponseEntity<Integer> cantidadEstadiasEnCurso() {
        Integer cantidad = estadiaService.cantidadEstadiasEnCurso();
        return ResponseEntity.status(HttpStatus.OK).body(cantidad);
    }

    //11.3. Recaudacion dia del hotel
    @Operation(
            summary = "Obtener recaudación del día",
            description = "Calcula la recaudación generada por las estadías cuyo check-in corresponde al día actual"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recaudación calculada correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @GetMapping("/kpi/recaudacion/hoy")
    public ResponseEntity<Double> recaudacionCheckInsDelDia() {
        Double recaudacion = estadiaService.recaudacionCheckInsDelDia();
        return ResponseEntity.status(HttpStatus.OK).body(recaudacion);
    }

    //11.4. Recaudacion por mes/anio
    @Operation(
            summary = "Obtener recaudación mensual",
            description = "Calcula la recaudación total agrupada por mes para el año indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recaudación obtenida correctamente"),
            @ApiResponse(responseCode = "403", description = "El usuario no posee permisos para realizar esta operación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @GetMapping("/kpi/recaudacion/mensual/{year}")
    public ResponseEntity<Map<String, Double>> recaudacionEstadiasPorMesAnio(
            @PathVariable Integer year) {
        Map<String, Double> recaudacionMensual = estadiaService.recaudacionEstadiasPorMesAnio(year);
        return ResponseEntity.status(HttpStatus.OK).body(recaudacionMensual);
    }
}