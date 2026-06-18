package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionUpdateDTO;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ErrorDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.HabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
@Tag(
        name = "Habitaciones",
        description = "Operaciones relacionadas con la gestión de habitaciones. incluye operaciones CRUD, pasar una habitacion a estado MANTENIMIENTO y listar habitaciones con estado DISPONIBLE"
)

public class HabitacionController {

    private final HabitacionService habitacionService;

    //1. Buscar habitacion por ID
    @Operation(
            summary = "Buscar habitación por ID",
            description = "Obtiene una habitación a partir de su identificador unico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habitación encontrada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Habitación no encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTOResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTOResponse.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<HabitacionDTOResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                habitacionService.findById(id)
        );
    }

    //2. Listar todas las habitaciones
    @Operation(
            summary = "Listar todas las habitaciones",
            description = "Obtiene todas las habitaciones sin importar su estado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de habitaciones obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<HabitacionDTOResponse>> getAll() {

        return ResponseEntity.ok(
                habitacionService.getAll());
    }


    //3. Crear habitacion
    @Operation(
            summary = "Crear una habitación",
            description = "Registra una nueva habitación en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Habitación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Habitacion con nro existente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @PostMapping
    public ResponseEntity<HabitacionDTOResponse> save(@Valid @RequestBody HabitacionDTORequest dto) {
        HabitacionDTOResponse response = habitacionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    //4. Borrar habitacion
    @Operation(
            summary = "Eliminar habitación",
            description = "Elimina una habitación del sistema a partir de su identificador unico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Habitación eliminada"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada"),
            @ApiResponse(responseCode = "409", description = "Habitación ocupada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        habitacionService.delete(id);

        return ResponseEntity.noContent().build();
    }

    //5.1. Actualizar habitacion
    @Operation(
            summary = "Actualizar habitación",
            description = "Actualiza los datos de una habitación existente sin modificar su ID ni número"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habitación actualizada"),
            @ApiResponse(responseCode = "400", description = "Solicitud con errores"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @PutMapping("/{id}")
    public ResponseEntity<HabitacionDTOResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody HabitacionUpdateDTO dto) {

        return ResponseEntity.ok(
                habitacionService.update(id, dto)
        );
    }


    //5.3. Actualizar estado de habitacion a MANTENIMINETO
    @Operation(
            summary = "Enviar habitación a mantenimiento",
            description = "Cambia el estado de la habitación a MANTENIMIENTO"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habitación actualizada"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada"),
            @ApiResponse(responseCode = "409", description = "La habitación no puede enviarse a mantenimiento en su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{id}/mantenimiento")
    public ResponseEntity<HabitacionDTOResponse> realizarMantenimiento(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                habitacionService.realizarMantenimiento(id)
        );
    }

    //6.1. Listar habitaciones disponibles
    @Operation(
            summary = "Listar habitaciones disponibles",
            description = "Obtiene todas las habitaciones con estado DISPONIBLE"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/disponibles")
    public ResponseEntity<List<HabitacionDTOResponse>> habitacionesDisponibles() {

        return ResponseEntity.ok(
                habitacionService.habitacionesDisponibles()
        );
    }

}