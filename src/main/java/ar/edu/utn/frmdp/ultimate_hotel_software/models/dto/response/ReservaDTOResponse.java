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
@Schema(description = "DTO utilizado para mostrar reservas")
public class ReservaDTOResponse {
    @Schema(description = "Identificador unico de la reserva", example = "1")
    private Long id;
    @Schema(description = "Fecha de checkin de la reserva")
    private LocalDate checkIn;
    @Schema(description = "Fecha de checkout de la reserva")
    private LocalDate checkOut;
    private Double total;
    @Schema(description = "Cantidad de pasajeros de la reserva", example = "4")
    private Integer cantidadPax;
    @Schema(description = "Estado de la reserva", example = "Confirmada")
    private EstadoReserva estadoReserva;
    @Schema(description = "Nombre del pasajero que realizo la reserva")
    private String nombre;
    @Schema(description = "Apellido del pasajero que realizo la reserva")
    private String apellido;
    @Schema(description = "Telefono del pasajero que realizo la reserva")
    private String telefono;
    @Schema(description = "Observacion/comentario aclarando solicitudes del pasajero", example = "EL cliente solicita servicio de cantina")
    private String comentario;

    @Schema(description = "Informacion del empleado asignado a la reserva")

    private EmpleadoReservaDTOResponse empleadoReservaDTOResponse;
    @Schema(description = "Habitacion asignada a la reserva")

    private HabitacionReservaDTOResponse habitacionReservaDTOResponse;

}
