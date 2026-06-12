package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.PasajeroDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.PasajeroDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PasajeroMapper {

    @Mapping(target = "datosPersona", source = "pasajeroDTORequest.datosPersonalesDTORequest")
    PasajeroEntity toEntity(PasajeroDTORequest pasajeroDTORequest);
    @Mapping(target = "datosPersonalesDTOResponse", source = "datosPersona")
    PasajeroDTOResponse toDTO(PasajeroEntity pasajeroEntity);


}
