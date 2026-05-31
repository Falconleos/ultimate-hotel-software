package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.EmpleadoMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.PasajeroMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.PasajeroDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.EmpleadoRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.PasajeroRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.PasajeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PasajeroServiceImpl implements PasajeroService {

    private final PasajeroRepository pasajeroRepository;
    private final PasajeroMapper pasajeroMapper;

    //1. Busqueda de pasajero
    //1.1 Devuelve entidad
    @Override
    public PasajeroEntity findEntityById(Long id) {
        return pasajeroRepository.findById(id)
                .orElseThrow( ()->new RuntimeException() );
    }

    //1.2 Devuelve DTOResponse
    public PasajeroDTOResponse getById (Long id) {
        return pasajeroMapper.toDTO(findEntityById(id));
    }

    //2. Listar pasajeros
    @Override
    public List<PasajeroDTOResponse> getAll() {
        return pasajeroRepository.findAll().stream()
                .map(pasajeroMapper::toDTO)
                .toList();
    }
}
