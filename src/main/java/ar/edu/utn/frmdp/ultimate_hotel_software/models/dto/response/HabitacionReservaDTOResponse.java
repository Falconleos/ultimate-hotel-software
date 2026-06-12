package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.TipoHabitacion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar informacion basica de la habitacion")
public class HabitacionReservaDTOResponse {
    @Schema(description = "Numero de habitacion dentro del hotel", example = "203")

    private Integer numero;
    @Schema(description = "Tipo de habitacion", example = "Doble")
    private TipoHabitacion tipoHabitacion;

}
