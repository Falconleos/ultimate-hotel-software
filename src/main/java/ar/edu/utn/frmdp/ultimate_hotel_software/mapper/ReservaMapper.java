package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.ReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ReservaEstadiaDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmpleadoMapper.class, HabitacionMapper.class})
public interface ReservaMapper {

    @Mapping(target = "empleadoEntity", ignore = true)
    @Mapping(target = "habitacionEntity", ignore = true)
    ReservaEntity toEntity(ReservaDTORequest request);

    @Mapping(target = "empleadoReservaDTOResponse", source = "empleadoEntity")
    @Mapping(target = "habitacionReservaDTOResponse", source = "habitacionEntity")
    @Mapping(target = "comentario", source = "observacion")
    @Mapping(target = "total", source = "total")
    ReservaDTOResponse toDto(ReservaEntity reserva);

    @Mapping(target = "numeroHabitacion", source = "habitacionEntity.numero")
    @Mapping(target = "comentario", source = "observacion")
    ReservaEstadiaDTOResponse toDtoReservaEstadia(ReservaEntity reserva);
}
