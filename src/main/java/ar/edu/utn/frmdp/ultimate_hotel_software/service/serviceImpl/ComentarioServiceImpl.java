package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.exception.InvalidIdException;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.ComentarioMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.ComentarioEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ComentarioDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ComentarioDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.ComentarioRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.ComentarioService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;
    private final EstadiaServiceImpl estadiaService;

    //1.1. Buscar comentario por id
    @Override
    public ComentarioDTOResponse getById (Long id) {
        return comentarioMapper.toDTO(findEntityById(id));
    }

    //1.2. Buscar comentario entidad por id
    public ComentarioEntity findEntityById(Long id) {
        return comentarioRepository.findById(id)
                .orElseThrow( ()->new InvalidIdException("Id de comentario invalido") );
    }

    //2.1. Listar comentarios por habitacion
    @Override
    public List<ComentarioDTOResponse> getComentariosHabitacion(Long habitacion_id) {
        return comentarioRepository.findByEstadiaReservaHabitacionEntityId(habitacion_id).stream()
                .map(comentarioMapper::toDTO)
                .toList();
    }

    //2.2. Listar comentarios por pasajero
    @Override
    public List<ComentarioDTOResponse> getComentarioPasajero(Long pasajero_id) {
        return comentarioRepository.findByEstadiaPasajeroEntityId(pasajero_id).stream()
                .map(comentarioMapper::toDTO)
                .toList();
    }

    //3. Crear comentario
    @Override
    public ComentarioDTOResponse createComentario(Long estadia_id, ComentarioDTORequest comentarioDTORequest) {

        //Busca estadia para agregar comentario
        EstadiaEntity estadia = estadiaService.getEntityById(estadia_id); //Lanza excepcion sino encuentra estadia

        //Mapeo comentario y completo datos del comentario
        ComentarioEntity comentarioEntity = comentarioMapper.toEntity(comentarioDTORequest);
        comentarioEntity.setEstadia(estadia);

        return comentarioMapper.toDTO(comentarioRepository.save(comentarioEntity));
    }

    //4. Eliminar comentario
    @Override
    public void deleteComentario(Long id) {
        ComentarioEntity comentario = findEntityById(id);
        comentarioRepository.delete(comentario);
    }

    //5. Actualizar comentario
    @Override
    public ComentarioDTOResponse updateComentario(Long id, ComentarioDTORequest comentarioDTORequest) {
        //Buscar entidad a modificar
        ComentarioEntity comentarioEntity = findEntityById(id);

        //Modificaciones
        comentarioEntity.setTexto(comentarioDTORequest.getTexto()); //Hibernate detecta cambio en el elemento y modifica la base de datos.

        //Guardado en repositorio y devolucion de DTO
        return comentarioMapper.toDTO(comentarioEntity);
    }
}