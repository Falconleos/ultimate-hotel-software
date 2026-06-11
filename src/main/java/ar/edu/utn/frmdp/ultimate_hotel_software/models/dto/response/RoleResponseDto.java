package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO utilizado para mostrar roles")

public record RoleResponseDto(
        @Schema(description = "Identificador unico del rol", example = "1")
        Long id,
        @Schema(description = "Nombre del rol", example = "LIMPIEZA")
        RoleType name
) {
}