package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.ComentarioEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ComentarioDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ComentarioDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    @Mapping(target = "estadiaId", source = "estadia.id")
    ComentarioDTOResponse toDTO(ComentarioEntity comentarioEntity);

    @Mapping(target = "estadia", ignore = true)
    ComentarioEntity toEntity(ComentarioDTORequest comentarioDTORequest);
}
