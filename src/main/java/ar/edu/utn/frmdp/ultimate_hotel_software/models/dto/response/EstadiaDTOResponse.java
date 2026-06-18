package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoEstadia;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar los atributos completos de estadias")
public class EstadiaDTOResponse {

    @Schema(description = "Identificador unico del comentario",
            example = "1")
    private Long id;

    @Schema(description = "Informacion de la reserva realizada")
    private ReservaEstadiaDTOResponse reservaEstadiaDTOResponse;
    @Schema(description = "Informacion del pasajero")

    private PasajeroDTOResponse pasajeroDTOResponse;
    @Schema(description = "Informacion del empleado asignado a la estadia")

    private EmpleadoReservaDTOResponse empleadoReservaDTOResponse;
    @Schema(description = "Costo total de la estadia", example = "USD$500")
    private Double total;

    private EstadoEstadia estado;

    @Schema(description = "Estado del pago de la estadia",example = "Pagada")
    private Boolean pagada;

}
