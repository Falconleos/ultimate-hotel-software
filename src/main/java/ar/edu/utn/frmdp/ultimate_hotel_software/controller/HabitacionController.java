package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionUpdateDTO;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.HabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
@Tag(
        name = "Habitaciones",
        description = "Operaciones relacionadas con la gestión de habitaciones"
)

public class HabitacionController {

    private final HabitacionService habitacionService;

    @Operation(
            summary = "Crear una habitación",
            description = "Registra una nueva habitación en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Habitación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<HabitacionDTOResponse> save(@Valid @RequestBody HabitacionDTORequest dto) {
        HabitacionDTOResponse response = habitacionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(
            summary = "Buscar habitación por ID",
            description = "Obtiene una habitación a partir de su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habitación encontrada"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HabitacionDTOResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                habitacionService.findById(id)
        );
    }

    @Operation(
            summary = "Actualizar habitación",
            description = "Actualiza los datos de una habitación existente sin modificar su ID ni número"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habitación actualizada"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<HabitacionDTOResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody HabitacionUpdateDTO dto) {

        return ResponseEntity.ok(
                habitacionService.update(id, dto)
        );
    }

    @Operation(
            summary = "Eliminar habitación",
            description = "Elimina una habitación del sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Habitación eliminada"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        habitacionService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Enviar habitación a mantenimiento",
            description = "Cambia el estado de la habitación a MANTENIMIENTO"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habitación actualizada"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    @PatchMapping("/{id}/mantenimiento")
    public ResponseEntity<HabitacionDTOResponse> realizarMantenimiento(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                habitacionService.realizarMantenimiento(id)
        );
    }

    @Operation(
            summary = "Listar habitaciones disponibles",
            description = "Obtiene todas las habitaciones con estado DISPONIBLE"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping("/disponibles")
    public ResponseEntity<List<HabitacionDTOResponse>> habitacionesDisponibles() {

        return ResponseEntity.ok(
                habitacionService.habitacionesDisponibles()
        );
    }

}