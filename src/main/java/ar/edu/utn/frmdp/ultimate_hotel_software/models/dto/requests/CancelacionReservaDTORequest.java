package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
public class CancelacionReservaDTORequest {

    private Long reserva_id;
    private String motivo;

}
