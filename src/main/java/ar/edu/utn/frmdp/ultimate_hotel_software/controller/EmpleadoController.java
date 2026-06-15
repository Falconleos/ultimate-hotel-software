package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ErrorDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
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
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
@Tag(
        name = "Empleados",
        description = "Operaciones relacionadas con la gestión de empleados"
)

public class EmpleadoController {

    private final EmpleadoService empleadoService;

    //1. Buscar empleado por id
    @Operation(
            summary = "Buscar empleado por ID",
            description = "Obtiene un empleado a partir de su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Empleado no encontrado",
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
    public ResponseEntity<EmpleadoDTOResponse> getById (@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.getById(id));
    }

    //2. Listar empleados
    @Operation(
            summary = "Listar todos los empleados",
            description = "Obtiene todos los empleados registrados en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de empleados obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<EmpleadoDTOResponse>> getAll() {
        return ResponseEntity.ok(empleadoService.getAll());
    }

    //3. Crear empleado
    @Operation(
            summary = "Crear un empleado",
            description = "Registra un nuevo empleado en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "409", description = "Empleado con datos duplicados en base de datos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<EmpleadoDTOResponse> createEmpleado (@RequestBody EmpleadoDTORequest empleadoDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.createEmpleado(empleadoDTORequest));
    }

    //4. Eliminar empleado
    @Operation(
            summary = "Eliminar empleado",
            description = "Elimina empleado del sistema a partir de un identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empleado eliminado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado (@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    //5. Modificar empleado
    //5.1. Actualizacion completa
    @Operation(
            summary = "Actualizar informacion del empleado",
            description = "Actualiza los datos de un emplaedo existente sin modificar su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrada"),
            @ApiResponse(responseCode = "409", description = "Empleado con datos duplicados en base de datos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTOResponse> updateEmpleado(@PathVariable Long id, @RequestBody EmpleadoDTORequest empleadoDTORequest) {
        return ResponseEntity.ok(empleadoService.updateEmpleado(id, empleadoDTORequest)
        );
    }

    //5.2. Cambiar turno
    @Operation(
            summary = "Actualizar turno del empleado",
            description = "Actualiza turno laboral del empleado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno del empleado actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrada"),
            @ApiResponse(responseCode = "409", description = "Actualizacion invalida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{id}/turno")
    public ResponseEntity<EmpleadoDTOResponse> cambiarTurno(@PathVariable Long id, @RequestParam Turno turno) {
        return ResponseEntity.ok(empleadoService.cambiarTurno(id, turno));
    }

    //5.3. Cambiar cargo
    @Operation(
            summary = "Actualizar cargo del empleado",
            description = "Actualiza cargo laboral activo del empleado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo del empleado actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrada"),
            @ApiResponse(responseCode = "409", description = "Actualizacion invalida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{id}/cargo")
    public ResponseEntity<EmpleadoDTOResponse> cambiarCargo(@PathVariable Long id, @RequestParam RoleType roleType) {
        return ResponseEntity.ok(empleadoService.cambiarCargo(id, roleType));
    }

    //5.3. Cambiar estado
    @Operation(
            summary = "Actualizar estado del empleado",
            description = "Conmuta estado laboral del empleado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado del empleado actualizado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<EmpleadoDTOResponse> cambiarEstado(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.cambiarEstado(id));
    }
}
