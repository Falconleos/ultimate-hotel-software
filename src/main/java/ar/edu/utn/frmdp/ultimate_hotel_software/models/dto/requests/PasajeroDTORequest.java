package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PasajeroDTORequest {

    @Valid
    @NotNull(message = "Los datos personales son obligatorios")
    private DatosPersonalesDTORequest datosPersonalesDTORequest;
}
