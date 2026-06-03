package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CancelacionReservaDTOResponse {

    private Long id;
    private ReservaDTOResponse reservaDTOResponse;
    private String motivo;
    private LocalDateTime fecha;

}
