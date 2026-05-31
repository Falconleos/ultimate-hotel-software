package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.EmpleadoRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    //1. Busqueda de empleado
    //1.1 Devuelve entidad
    @Override
    public EmpleadoEntity findEntityById(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow( ()->new RuntimeException() );
    }

    //1.2 Devuelve Response entity
    public ResponseEntity<EmpleadoEntity> getById (Long id)

}
