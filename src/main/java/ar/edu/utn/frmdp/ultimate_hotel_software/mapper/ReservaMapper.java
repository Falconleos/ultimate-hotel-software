package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.ReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ReservaEstadiaDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {EmpleadoMapper.class, HabitacionMapper.class})
public interface ReservaMapper {

    @Mapping(target = "empleado", ignore = true)
    @Mapping(target = "habitacion", ignore = true)
    ReservaEntity toEntity(ReservaDTORequest request);

    @Mapping(target = "empleadoReservaDTOResponse", source = "empleado")
    @Mapping(target = "habitacionReservaDTOResponse", source = "habitacion")
    ReservaDTOResponse toDto(ReservaEntity reserva);

    ReservaEstadiaDTOResponse toDtoReservaEstadia(ReservaEntity reserva);

}
