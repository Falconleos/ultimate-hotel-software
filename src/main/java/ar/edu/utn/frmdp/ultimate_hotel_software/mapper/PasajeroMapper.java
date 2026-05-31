package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.PasajeroDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.PasajeroDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasajeroMapper {

    PasajeroEntity toEntity(PasajeroDTORequest pasajeroDTORequest);
    PasajeroDTOResponse toDTO(PasajeroEntity pasajeroEntity);
    PasajeroDTOResponse toDto(PasajeroEntity pasajeroEntity);

}
