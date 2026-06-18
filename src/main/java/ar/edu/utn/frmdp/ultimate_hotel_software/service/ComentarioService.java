package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.ComentarioEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ComentarioDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ComentarioDTOResponse;

import java.util.List;

public interface ComentarioService {

    ComentarioDTOResponse getById (Long id);
    ComentarioDTOResponse createComentario(ComentarioDTORequest comentarioDTORequest);
    List<ComentarioDTOResponse> getComentariosHabitacion (Long habitacion_id);
    List<ComentarioDTOResponse> getComentarioPasajero (Long pasajero_id);
    ComentarioDTOResponse updateComentario (Long id, ComentarioDTORequest comentarioDTORequest);
    void deleteComentario (Long id);
}
