package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import lombok.Getter;

@Getter
public class CancelacionReservaDTORequest {

    private Long reserva_id;
    private String motivo;

}
