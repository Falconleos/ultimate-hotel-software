package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import jakarta.validation.constraints.NotNull;

public record RoleRequestDto(@NotNull RoleType name) {
}