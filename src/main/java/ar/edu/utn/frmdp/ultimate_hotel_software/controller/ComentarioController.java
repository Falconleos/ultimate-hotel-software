package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ComentarioDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ComentarioDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.ComentarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
@Tag(
        name = "Comentarios",
        description = "Operaciones relacionadas con la gestión de comentarios"
)
public class ComentarioController {

    private final ComentarioService comentarioService;

    //1. Buscar comentario por id
    @GetMapping("/{id}")
    public ResponseEntity<ComentarioDTOResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(comentarioService.getById(id));
    }

    //2.1. Listar comentarios por habitacion
    @GetMapping("/habitacion/{habitacionId}")
    public ResponseEntity<List<ComentarioDTOResponse>> getComentariosHabitacion(@PathVariable Long habitacionId) {
        return ResponseEntity.ok(comentarioService.getComentariosHabitacion(habitacionId));
    }

    //2.2. Listar comentarios por pasajero
    @GetMapping("/pasajero/{pasajeroId}")
    public ResponseEntity<List<ComentarioDTOResponse>> getComentariosPasajero(@PathVariable Long pasajeroId) {
        return ResponseEntity.ok(comentarioService.getComentarioPasajero(pasajeroId));
    }

    //3. Crear comentario
    @PostMapping("/{id}")
    public ResponseEntity<ComentarioDTOResponse> createComentario(@PathVariable Long id, @RequestBody ComentarioDTORequest comentarioDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioService.createComentario(id, comentarioDTORequest));
    }

    //4. Eliminar comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        comentarioService.deleteComentario(id);
        return ResponseEntity.noContent().build();
    }

    //5. Actualizar comentario
    @PutMapping("/{id}")
    public ResponseEntity<ComentarioDTOResponse> updateComentario(@PathVariable Long id, @RequestBody ComentarioDTORequest comentarioDTORequest) {
        return ResponseEntity.ok(comentarioService.updateComentario(id, comentarioDTORequest));
    }
}