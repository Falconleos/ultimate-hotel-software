package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ComentarioDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ComentarioDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ErrorDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Buscar comentario por ID",
            description = "Obtiene un comentario a partir de su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentario encontrado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comentario no encontrado",
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
    public ResponseEntity<ComentarioDTOResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(comentarioService.getById(id));
    }

    //2.1. Listar comentarios por habitacion
    @Operation(
            summary = "Listar comentarios por habitacion",
            description = "Obtiene comentarios a partir de un identificador de habitacion"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentarios encontrados"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Habitacion no encontrada",
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
    @GetMapping("/habitacion/{habitacionId}")
    public ResponseEntity<List<ComentarioDTOResponse>> getComentariosHabitacion(@PathVariable Long habitacionId) {
        return ResponseEntity.ok(comentarioService.getComentariosHabitacion(habitacionId));
    }

    //2.2. Listar comentarios por pasajero
    @Operation(
            summary = "Listar comentarios por pasajero",
            description = "Obtiene comentarios a partir de un identificador de pasajeros"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentarios encontrados"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pasajero no encontrado",
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
    @GetMapping("/pasajero/{pasajeroId}")
    public ResponseEntity<List<ComentarioDTOResponse>> getComentariosPasajero(@PathVariable Long pasajeroId) {
        return ResponseEntity.ok(comentarioService.getComentarioPasajero(pasajeroId));
    }

    //3. Crear comentario
    @Operation(
            summary = "Crear un comentario",
            description = "Registra un nuevo comentario en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comentario creado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de comentario invalidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTOResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estadia no encontrada",
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
    @PostMapping("/{id}")
    public ResponseEntity<ComentarioDTOResponse> createComentario(@PathVariable Long id, @RequestBody ComentarioDTORequest comentarioDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioService.createComentario(id, comentarioDTORequest));
    }

    //4. Eliminar comentario
    @Operation(
            summary = "Eliminar un comentario",
            description = "Eliminar comentario del sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comentario eliminado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comentario no encontrado",
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        comentarioService.deleteComentario(id);
        return ResponseEntity.noContent().build();
    }

    //5. Actualizar comentario
    @Operation(
            summary = "Actualizar comentario",
            description = "Actualiza los datos de un comentario"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentario actualizado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud con errores",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTOResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comentario no encontrado",
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
    @PutMapping("/{id}")
    public ResponseEntity<ComentarioDTOResponse> updateComentario(@PathVariable Long id, @RequestBody ComentarioDTORequest comentarioDTORequest) {
        return ResponseEntity.ok(comentarioService.updateComentario(id, comentarioDTORequest));
    }
}