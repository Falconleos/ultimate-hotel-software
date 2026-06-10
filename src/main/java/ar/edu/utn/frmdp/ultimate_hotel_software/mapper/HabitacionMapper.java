package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionUpdateDTO;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionReservaDTOResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {

    HabitacionEntity toEntity(HabitacionDTORequest dto);

    HabitacionDTOResponse toResponse(HabitacionEntity habitacion);

    HabitacionReservaDTOResponse toDtoReserva(HabitacionEntity habitacion);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numero", ignore = true)
    void updateHabitacionFromDto(
            HabitacionUpdateDTO dto,
            @MappingTarget HabitacionEntity habitacion
    );
}