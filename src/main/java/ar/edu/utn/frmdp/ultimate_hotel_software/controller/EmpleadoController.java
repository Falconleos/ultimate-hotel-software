package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor

public class EmpleadoController {

    private final EmpleadoService empleadoService;

    //1. Buscar empleado por id
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTOResponse> getById (@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.getById(id));
    }

    //2. Ver empleados
    @GetMapping
    public ResponseEntity<List<EmpleadoDTOResponse>> getAll() {
        return ResponseEntity.ok(empleadoService.getAll());
    }

    //3. Crear empleado
    @PostMapping
    public ResponseEntity<EmpleadoDTOResponse> createEmpleado (@RequestBody EmpleadoDTORequest empleadoDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.createEmpleado(empleadoDTORequest));
    }

    //4. Eliminar empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado (@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    //5. Modificar empleado
    //5.1. Actualizacion completa
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTOResponse> updateEmpleado(@PathVariable Long id, @RequestBody EmpleadoDTORequest empleadoDTORequest) {
        return ResponseEntity.ok(empleadoService.updateEmpleado(id, empleadoDTORequest)
        );
    }

    //5.2. Cambiar turno
    @PatchMapping("/{id}/turno")
    public ResponseEntity<EmpleadoDTOResponse> cambairTurno(@PathVariable Long id, @RequestParam Turno turno) {
        return ResponseEntity.ok(empleadoService.cambiarTurno(id, turno));
    }

    //5.3. Cambiar cargo
    @PatchMapping("/{id}/cargo")
    public ResponseEntity<EmpleadoDTOResponse> cambiarCargo(@PathVariable Long id, @RequestParam RoleType roleType) {
        return ResponseEntity.ok(empleadoService.cambiarCargo(id, roleType));
    }

    //5.3. Cambiar estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<EmpleadoDTOResponse> cambiarEstado(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.cambiarEstado(id));
    }
}
