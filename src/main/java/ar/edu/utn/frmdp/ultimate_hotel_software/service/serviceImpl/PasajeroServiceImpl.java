package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.exception.InvalidIdException;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.PasajeroMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ComentarioDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.PasajeroDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ComentarioDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.PasajeroDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.DatosPersonalesEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.PasajeroRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.ComentarioService;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.PasajeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PasajeroServiceImpl implements PasajeroService {

    private final PasajeroRepository pasajeroRepository;
    private final PasajeroMapper pasajeroMapper;
    private final ComentarioService comentarioService;

    //1. Busqueda de pasajero
    //1.1 Devuelve entidad
    @Override
    public PasajeroEntity findEntityById(Long id) {
        return pasajeroRepository.findById(id)
                .orElseThrow( ()->new InvalidIdException("id de pasajero invalido") );
    }

    //1.2 Devuelve DTOResponse
    @Override
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

    //3. Crear pasajero
    @Override
    public PasajeroDTOResponse createPasajero(PasajeroDTORequest pasajeroDTORequest) {

        //Mapeo a entidad
        PasajeroEntity pasajeroEntity = pasajeroMapper.toEntity(pasajeroDTORequest);

        //Guardado en base de datos
        return pasajeroMapper.toDTO(pasajeroRepository.save(pasajeroEntity));
    }

    //4. Borrar pasajero
    @Override
    public void deletePasajero(Long id) {

        PasajeroEntity pasajeroEntity = findEntityById(id);
        pasajeroRepository.delete(pasajeroEntity);
    }

    //5. Actualizaciones
    //5.1. Actualizar pasajero (completo)
    @Override
    @Transactional
    public PasajeroDTOResponse updatePasajero(Long id, PasajeroDTORequest pasajeroDTORequestModificado) {

        PasajeroEntity pasajero = findEntityById(id);
        DatosPersonalesEntity datosPersonalesEntity = pasajero.getDatosPersona();

        datosPersonalesEntity.setNombre(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getNombre());
        datosPersonalesEntity.setApellido(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getApellido());
        datosPersonalesEntity.setEmail(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getEmail());
        datosPersonalesEntity.setTelefono(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getTelefono());

        pasajero.setDatosPersona(datosPersonalesEntity);

        return pasajeroMapper.toDTO(pasajeroRepository.save(pasajero));
    }
}