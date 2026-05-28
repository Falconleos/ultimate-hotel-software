package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.Habitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionReservaDTOResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {

    HabitacionReservaDTOResponse toDtoReserva(Habitacion habitacion);

}
