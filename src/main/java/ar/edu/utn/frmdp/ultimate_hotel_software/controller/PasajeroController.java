package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.PasajeroDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ErrorDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.PasajeroDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.PasajeroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pasajeros")
@RequiredArgsConstructor
@Tag(
        name = "Pasajeros",
        description = "Operaciones relacionadas con la gestión de pasajeros. Incluye operaciones CRUD"
)

public class PasajeroController {

    private final PasajeroService pasajeroService;

    //1. Buscar pasajero por id
    @Operation(
            summary = "Buscar pasajero por ID",
            description = "Obtiene un pasajero a partir de su identificador unico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pasajero encontrado"),
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
    @GetMapping("/{id}")
    public ResponseEntity<PasajeroDTOResponse> getById (@PathVariable Long id) {
        return ResponseEntity.ok(pasajeroService.getById(id));
    }

    //2. Listar pasajeros
    @Operation(
            summary = "Listar todos los pasajeros",
            description = "Obtiene todos los pasajeros registrados en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de pasajeros obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<PasajeroDTOResponse>> getAll(){
        return ResponseEntity.ok(pasajeroService.getAll());
    }

    //3. Crear pasajero
    @Operation(
            summary = "Crear un pasajero",
            description = "Registra un nuevo pasajero en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pasajero creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Pasajero con datos duplicados en base de datos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping
    public ResponseEntity<PasajeroDTOResponse> createPasajero(@RequestBody PasajeroDTORequest pasajeroDTORequest) {
        return ResponseEntity.ok(pasajeroService.createPasajero(pasajeroDTORequest));
    }


   //------------>>>> /*POR el momento tira error por relaciones con estadia*/
    /*
    //4. Eliminar pasajero
    @Operation(
            summary = "Eliminar pasajero",
            description = "Elimina pasajero del sistema a partir de un identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pasajero eliminado"),
            @ApiResponse(responseCode = "404", description = "Pasajero no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePasajero(@PathVariable Long id) {
        pasajeroService.deletePasajero(id);
        return ResponseEntity.noContent().build();
    }
    */


    //5. Actualizar pasajero
    @Operation(
            summary = "Actualizar informacion del pasajero",
            description = "Actualiza los datos de un pasajero existente sin modificar su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pasajero actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Pasajero no encontrada"),
            @ApiResponse(responseCode = "409", description = "Pasajero con datos duplicados en base de datos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PutMapping("/{id}")
    public ResponseEntity<PasajeroDTOResponse> updatePasajero(@PathVariable Long id,@RequestBody PasajeroDTORequest pasajeroDTORequest) {
        return ResponseEntity.ok(pasajeroService.updatePasajero(id, pasajeroDTORequest));
    }
}
