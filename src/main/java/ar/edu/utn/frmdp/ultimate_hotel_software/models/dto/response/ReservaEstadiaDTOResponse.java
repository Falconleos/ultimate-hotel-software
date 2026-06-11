package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoReserva;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservaEstadiaDTOResponse {

    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer cantidadPax;
    private String comentario;
    private Integer numeroHabitacion;
}
