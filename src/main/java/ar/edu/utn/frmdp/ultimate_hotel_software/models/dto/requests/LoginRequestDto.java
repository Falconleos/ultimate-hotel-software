package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(

        @NotBlank
        String username,

        @NotBlank
        String password
) {}
