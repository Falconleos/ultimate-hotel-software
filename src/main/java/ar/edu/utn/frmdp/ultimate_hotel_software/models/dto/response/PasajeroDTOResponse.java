package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.DatosPersonalesEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar pasajeros")
public class PasajeroDTOResponse {

    @Schema(
            description = "Identificador único del pasajero",
            example = "1"
    )
    private Long id;
    @Schema(description = "Datos personales del pasajero", example = "Nombre, apellido, telefono")
    private DatosPersonalesDTOResponse datosPersonalesDTOResponse;
}
