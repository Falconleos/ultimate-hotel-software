package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.Empleado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    EmpleadoReservaDTOResponse toDtoReservaEmpleado(Empleado empleado);
}
