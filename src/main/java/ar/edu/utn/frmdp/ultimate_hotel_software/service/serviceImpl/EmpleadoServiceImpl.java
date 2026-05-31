package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.EmpleadoMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.EmpleadoRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;

    //1. Busqueda de empleado
    //1.1 Devuelve entidad
    @Override
    public EmpleadoEntity findEntityById(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow( ()->new RuntimeException() );
    }

    //1.2 Devuelve ReservaDTOResponse
    public EmpleadoReservaDTOResponse getById (Long id) {
        return empleadoMapper.toDTO(findEntityById(id));
    }

    //2. Listar empleados
    @Override
    public List<EmpleadoReservaDTOResponse> getAll() {
        return empleadoRepository.findAll().stream()
                .map(empleadoMapper::toDTO)
                .toList();
    }
}
