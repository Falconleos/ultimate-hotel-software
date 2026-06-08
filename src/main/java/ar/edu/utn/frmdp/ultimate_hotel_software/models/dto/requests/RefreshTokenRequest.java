package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(

        @NotBlank(message = "Refresh accessToken is required")
        String refreshToken

) {
}