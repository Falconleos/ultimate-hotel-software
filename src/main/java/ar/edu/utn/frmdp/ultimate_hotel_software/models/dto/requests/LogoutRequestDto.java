package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
@Schema(description = "DTO utlizado para implementar Spring Security durante logout")
public record LogoutRequestDto(@NotBlank String refreshToken) {
}
