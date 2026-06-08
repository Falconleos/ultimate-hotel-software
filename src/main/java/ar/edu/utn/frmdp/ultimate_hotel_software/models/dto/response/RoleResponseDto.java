package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;

public record RoleResponseDto(
        Long id,
        RoleType name
) {
}