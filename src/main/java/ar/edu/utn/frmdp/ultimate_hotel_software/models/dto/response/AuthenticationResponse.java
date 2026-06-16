package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import lombok.*;

@Builder
public record AuthenticationResponse(
        String accessToken,
        String refreshToken
) {


}
