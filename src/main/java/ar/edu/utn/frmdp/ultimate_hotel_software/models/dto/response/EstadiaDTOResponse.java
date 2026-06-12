package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar estadias")
public class EstadiaDTOResponse {
    private Long id;

    @Schema(description = "Informacion de la reserva realizada")

    private ReservaEstadiaDTOResponse reservaEstadiaDTOResponse;
    @Schema(description = "Informacion del pasajero")

    private PasajeroDTOResponse pasajeroDTOResponse;
    @Schema(description = "Informacion del empleado asignado a la estadia")

    private EmpleadoReservaDTOResponse empleadoReservaDTOResponse;
    @Schema(description = "Costo total de la estadia", example = "USD$500")
    private Double total;
    @Schema(description = "Observacion/comentario aclarando solicitudes del pasajero", example = "EL cliente solicita servicio de cantina")
    private String observacion;

    @Schema(description = "Estado del pago de la estadia",example = "Pagada")
    private Boolean pagada;

}
