package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.ComentarioMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.ComentarioEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ComentarioDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ComentarioDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.ComentarioRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ComentarioServiceImpl implements ComentarioService {

    private ComentarioRepository comentarioRepository;
    private ComentarioMapper comentarioMapper;
    private EstadiaServiceImpl estadiaService;

    @Override
    public ComentarioDTOResponse crearComentario(Long estadia_id, ComentarioDTORequest comentarioDTORequest) {

        //Busca estadia para agregar comentario
        EstadiaEntity estadia = estadiaService.getEntityById(estadia_id);

        //Mapeo comentario y completo datos del comentario
        ComentarioEntity comentarioEntity = comentarioMapper.toEntity(comentarioDTORequest);
        comentarioEntity.setEstadia(estadia);

        return comentarioMapper.toDTO(comentarioRepository.save(comentarioEntity));


    }
}
