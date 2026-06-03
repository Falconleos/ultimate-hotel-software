package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.CancelacionReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.CancelacionReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.CancelacionReservaDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = ReservaMapper.class)
public interface CancelacionReservaMapper {


    CancelacionReservaEntity toEntity(CancelacionReservaDTORequest cancelacionReservaDTORequest);

    @Mapping(target = "reservaDTOResponse",source = "reservaEntity")
    CancelacionReservaDTOResponse toDto(CancelacionReservaEntity cancelacionReservaEntity);

}
