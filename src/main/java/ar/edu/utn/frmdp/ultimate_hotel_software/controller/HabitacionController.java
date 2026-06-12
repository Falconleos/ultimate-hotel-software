package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionUpdateDTO;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.HabitacionService;
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

    @PostMapping
    public ResponseEntity<HabitacionDTOResponse> save(
            @Valid @RequestBody HabitacionDTORequest dto) {

        HabitacionDTOResponse response = habitacionService.save(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitacionDTOResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                habitacionService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitacionDTOResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody HabitacionUpdateDTO dto) {

        return ResponseEntity.ok(
                habitacionService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        habitacionService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/mantenimiento")
    public ResponseEntity<HabitacionDTOResponse> realizarMantenimiento(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                habitacionService.realizarMantenimiento(id)
        );
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<HabitacionDTOResponse>> habitacionesDisponibles() {

        return ResponseEntity.ok(
                habitacionService.habitacionesDisponibles()
        );
    }
}