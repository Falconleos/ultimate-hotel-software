package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.ComentarioEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ComentarioDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ComentarioDTOResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ComentarioMapper {

    ComentarioEntity toEntity(ComentarioDTORequest comentarioDTORequest);
    ComentarioDTOResponse toDTO(ComentarioEntity comentarioEntity);
}
