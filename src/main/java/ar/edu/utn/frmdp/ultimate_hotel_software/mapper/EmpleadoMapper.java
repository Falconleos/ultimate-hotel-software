package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    EmpleadoEntity toEntity(EmpleadoDTORequest empleadoDTORequest);
    EmpleadoDTOResponse toDTO(EmpleadoEntity empleadoEntity);

    EmpleadoReservaDTOResponse toDtoReservaEmpleado(EmpleadoEntity empleadoEntity); //DTO para reservas
}
