package ar.edu.utn.frmdp.ultimate_hotel_software.mapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Role;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    // 1. De DTO Request a Entidad (Hacia la Base de Datos)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "datosPersona", source = "empleadoDTORequest.datosPersonalesDTORequest")
    EmpleadoEntity toEntity(EmpleadoDTORequest empleadoDTORequest);

    // 2. De Entidad a DTO Response (Hacia Postman)
    // Desglosamos el @Embedded datosPersona para que llene los campos sueltos del response
    @Mapping(source = "empleadoEntity.datosPersona.nombre", target = "nombre")
    @Mapping(source = "empleadoEntity.datosPersona.apellido", target = "apellido")
    @Mapping(source = "empleadoEntity.datosPersona.dni", target = "dni")
    @Mapping(source = "empleadoEntity.datosPersona.email", target = "email")
    @Mapping(source = "empleadoEntity.datosPersona.telefono", target = "telefono")
    EmpleadoDTOResponse toDTO(EmpleadoEntity empleadoEntity);

    // Mapeo secundario para reservas
    @Mapping(source = "datosPersona.nombre", target = "nombre")
    @Mapping(source = "datosPersona.apellido", target = "apellido")
    EmpleadoReservaDTOResponse toDtoReservaEmpleado(EmpleadoEntity empleadoEntity);


    default Role mapRoleTypeToRole(RoleType roleType) {
        if (roleType == null) {
            return null;
        }
        return Role.builder()
                .name(roleType)
                .build();
    }
}