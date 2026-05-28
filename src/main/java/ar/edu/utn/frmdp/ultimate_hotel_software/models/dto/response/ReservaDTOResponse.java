package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoReserva;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservaDTOResponse {

    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer cantidadPax;
    private EstadoReserva estadoReserva;
    private String nombre;
    private String apellido;
    private String telefono;
    private String comentario;

    private EmpleadoReservaDTOResponse empleadoReservaDTOResponse;
    private HabitacionReservaDTOResponse habitacionReservaDTOResponse;

}
