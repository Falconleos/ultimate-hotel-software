package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EstadiaDTORequest {

    @NotNull(message = "La reserva es obligatoria")
    private Long reservaId;
    @NotNull(message = "El pasajero es obligatorio")
    private Long pasajeroId;
    @NotNull(message = "El empleado que realiza el check in es obligatorio")
    private Long empleadoId;
    @NotNull(message = "indique obligatoriamente si se abona o no al realizar el check in")
    private Boolean pagada;

}
