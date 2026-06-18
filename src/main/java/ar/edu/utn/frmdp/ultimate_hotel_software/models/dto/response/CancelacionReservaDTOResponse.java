package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar datos de las reservas cancelada")
public class CancelacionReservaDTOResponse {
    @Schema(description = "Identificador unico de la reserva cancelada",
            example = "1")
    private Long id;
    @Schema(description = "Informacion de la reserva cancelada")

    private ReservaDTOResponse reservaDTOResponse;
    @Schema(description = "Motivo de la cancelacion",
            example = "Problemas de salud")
    private String motivo;

    @Schema(description = "Fecha de la cancelacion")
    private LocalDateTime fecha;
}
