package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Schema(description = "DTO utilizado para crear estadias")
public class EstadiaDTORequest {

    @NotNull(message = "La reserva es obligatoria")
    @Schema(description = "Identificador unico de la reserva")
    private Long reservaId;
    @NotNull(message = "El pasajero es obligatorio")
    @Schema(description = "Identificador unico del pasajero")
    private Long pasajeroId;
    @NotNull(message = "El empleado que realiza el check in es obligatorio")
    @Schema(description = "Identificador unico del empleado asignado")
    private Long empleadoId;
    @NotNull(message = "indique obligatoriamente si se abona o no al realizar el check in")
    @Schema(description = "Estado del pago de la estadia",example = "Pagada")
    private Boolean pagada;

}
