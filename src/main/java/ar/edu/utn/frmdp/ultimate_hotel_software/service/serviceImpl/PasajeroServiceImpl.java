package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.exception.InvalidIdException;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.PasajeroDuplicadoException;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.PasajeroNoEncontradoException;
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

    //1. Busqueda de pasajero
    //1.1 Devuelve entidad
    @Override
    public PasajeroEntity findEntityById(Long id) {
        return pasajeroRepository.findById(id)
                .orElseThrow( ()->new PasajeroNoEncontradoException("Pasajero no encontrado") );
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

        if (pasajeroRepository.existsByDatosPersonaEmail(
                pasajeroDTORequest.getDatosPersonalesDTORequest().getEmail())) {
            throw new PasajeroDuplicadoException("Ya existe un pasajero con ese email");
        }
        if (pasajeroRepository.existsByDatosPersonaDni(pasajeroDTORequest.getDatosPersonalesDTORequest().getDni())) {
            throw new PasajeroDuplicadoException("Ya existe un pasajero con ese email");
        }
        if (pasajeroRepository.existsByDatosPersonaTelefono(
                pasajeroDTORequest.getDatosPersonalesDTORequest().getTelefono())) {

            throw new PasajeroDuplicadoException("Ya existe un pasajero con ese telefono");
        }
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

        //Validaciones solo contra otros registros, nunca contra sí mismo
        if (!datosPersonalesEntity.getDni().equals(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getDni()) &&
                pasajeroRepository.existsByDatosPersonaDni(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getDni())) {
            throw new PasajeroDuplicadoException("DNI ya en uso");
        }

        if (!datosPersonalesEntity.getEmail().equals(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getEmail()) &&
                pasajeroRepository.existsByDatosPersonaEmail(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getEmail())) {
            throw new PasajeroDuplicadoException("Email ya en uso");
        }

        if (!datosPersonalesEntity.getTelefono().equals(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getTelefono()) &&
                pasajeroRepository.existsByDatosPersonaTelefono(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getTelefono())) {
            throw new PasajeroDuplicadoException("Telefono ya en uso");
        }

        //Modificaciones
        datosPersonalesEntity.setNombre(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getNombre());
        datosPersonalesEntity.setApellido(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getApellido());
        datosPersonalesEntity.setEmail(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getEmail());
        datosPersonalesEntity.setTelefono(pasajeroDTORequestModificado.getDatosPersonalesDTORequest().getTelefono());

        pasajero.setDatosPersona(datosPersonalesEntity);

        return pasajeroMapper.toDTO(pasajeroRepository.save(pasajero));
    }
}