package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Schema(description = "DTO utilizado para crear pasajeros")
public class PasajeroDTORequest {

    @Valid
    @NotNull(message = "Los datos personales son obligatorios")
    @Schema(description = "Datos personales del pasajero", example = "Nombre, apellido, telefono")
    private DatosPersonalesDTORequest datosPersonalesDTORequest;
}
