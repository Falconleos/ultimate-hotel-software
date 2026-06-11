package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.PasajeroDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.PasajeroDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.PasajeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pasajeros")
@RequiredArgsConstructor

public class PasajeroController {

    private final PasajeroService pasajeroService;

    //1. Buscar pasajero por id
    @GetMapping("/{id}")
    public ResponseEntity<PasajeroDTOResponse> getById (@PathVariable Long id) {
        return ResponseEntity.ok(pasajeroService.getById(id));
    }

    //2. Listar pasajeros
    @GetMapping
    public ResponseEntity<List<PasajeroDTOResponse>> getAll(){
        return ResponseEntity.ok(pasajeroService.getAll());
    }

    //3. Crear pasajero
    @PostMapping
    public ResponseEntity<PasajeroDTOResponse> createPasajero(@RequestBody PasajeroDTORequest pasajeroDTORequest) {
        return ResponseEntity.ok(pasajeroService.createPasajero(pasajeroDTORequest));
    }

    //4. Eliminar pasajero
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePasajero(@PathVariable Long id) {
        pasajeroService.deletePasajero(id);
        return ResponseEntity.noContent().build();
    }
    
    //5. Actualizar pasajero
    @PutMapping
    public ResponseEntity<PasajeroDTOResponse> updatePasajero(@PathVariable Long id,@RequestBody PasajeroDTORequest pasajeroDTORequest) {
        return ResponseEntity.ok(pasajeroService.updatePasajero(id, pasajeroDTORequest));
    }
}
