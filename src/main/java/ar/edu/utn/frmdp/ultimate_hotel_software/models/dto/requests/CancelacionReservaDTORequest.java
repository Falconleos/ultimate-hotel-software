package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "DTO utilizado para crear cancelaciones de reserva. ")

public class CancelacionReservaDTORequest {

    @Schema(description = "Identificador unico de la reserva a cancelar")
    private Long reserva_id;
    @Schema(description = "Motivo del pasajero para cancelar la reserva", example = "Problemas de salud")
    private String motivo;

}
