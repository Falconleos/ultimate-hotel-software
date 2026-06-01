package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EstadiaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EstadiaDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {EmpleadoMapper.class,
                ReservaMapper.class,
                PasajeroMapper.class})
public interface EstadiaMapper {

    EstadiaEntity toEntity(EstadiaDTORequest estadiaDTORequest);

    @Mapping(target = "reservaEstadiaDTOResponse", source = "reservaEntity")
    @Mapping(target = "pasajeroDTOResponse", source = "pasajeroEntity")
    @Mapping(target = "empleadoReservaDTOResponse", source = "empleadoEntity")
    @Mapping(target = "observacion", source = "reservaEntity.observacion")
    EstadiaDTOResponse toDto(EstadiaEntity estadiaEntity);

}
