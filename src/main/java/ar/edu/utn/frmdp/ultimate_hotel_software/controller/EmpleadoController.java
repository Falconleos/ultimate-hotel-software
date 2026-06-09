package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl.EmpleadoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor

public class EmpleadoController {

    private final EmpleadoServiceImpl empleadoService;

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
        return ResponseEntity.status(HttpStatus.OK).body(empleadoService.createEmpleado(empleadoDTORequest));
    }

    //4. Eliminar empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado (@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
