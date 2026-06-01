package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EstadiaDTORequest {

    @NotNull(message = "La reserva es obligatoria")
    private Long reservaId;
    @NotNull(message = "El pasajero es obligatorio")
    private Long pasajeroId;
    @NotNull(message = "indique obligatoriamente si paga o no al realizar el check in")
    private Boolean pagada;

}
