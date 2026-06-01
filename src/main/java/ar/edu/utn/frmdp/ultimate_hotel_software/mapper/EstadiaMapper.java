package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EstadiaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EstadiaDTOResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {EmpleadoMapper.class,
                HabitacionMapper.class,
                ReservaMapper.class,
                PasajeroMapper.class})
public interface EstadiaMapper {

    EstadiaEntity toEntity(EstadiaDTORequest estadiaDTORequest);
    EstadiaDTOResponse toDto(EstadiaEntity estadiaEntity);

}
