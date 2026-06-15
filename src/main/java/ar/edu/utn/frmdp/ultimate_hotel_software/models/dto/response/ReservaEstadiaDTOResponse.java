package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar los datos basicos de la reserva ")

public class ReservaEstadiaDTOResponse {
    @Schema(description = "Identificador unico de la reserva ", example = "1")
    private Long id;
    @Schema(description = "Fecha de checkin de la reserva")
    private LocalDate checkIn;
    @Schema(description = "Fecha de checkout de la reserva")
    private LocalDate checkOut;
    @Schema(description = "Cantidad de pasajeros de la reserva", example = "4")
    private Integer cantidadPax;
    @Schema(description = "Observacion/comentario aclarando solicitudes del pasajero", example = "EL cliente solicita servicio de cantina")
    private String observacion;
    @Schema(description = "Numero de habitacion dentro del hotel", example = "203")
    private Integer numeroHabitacion;
}
