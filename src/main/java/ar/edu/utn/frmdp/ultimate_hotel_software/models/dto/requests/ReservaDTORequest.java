package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Schema(description = "DTO utilizado para crear reservas")
public class ReservaDTORequest {

    @FutureOrPresent(message = "El check in no puede ser pasada")
    @Schema(description = "Fecha de checkin de la reserva")
    private LocalDate checkIn;
    @FutureOrPresent(message = "El check out no puede ser pasado")
    @Schema(description = "Fecha de checkout de la reserva")
    private LocalDate checkOut;
    @Min(value = 1,message = "el valor minimo debe ser 1")
    @Schema(description = "Cantidad de pasajeros de la reserva", example = "4")
    private Integer cantidadPax;
    @NotBlank
    @Size(min = 2, max = 100, message = "valor mínimo 2 caracteres y máximo 100")
    @Schema(description = "Nombre del pasajero que realizo la reserva")
    private String nombre;
    @Size(min = 2, max = 100, message = "valor mínimo 2 caracteres y máximo 100")
    @Schema(description = "Apellido del pasajero que realizo la reserva")
    private String apellido;
    @NotBlank
    @Schema(description = "Telefono del pasajero que realizo la reserva")
    private String telefono;
    @Schema(description = "Observacion/comentario aclarando solicitudes del pasajero", example = "EL cliente solicita servicio de cantina")

    private String observacion;

    @NotNull
    @Schema(description = "Identificador unico del empleado asignado a la reserva")
    private Long empleadoId;
    @NotNull
    @Schema(description = "Identificador unico de la habitacion asignada a la reserva")
    private Long habitacionId;

}
