package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EstadiaDTOResponse {

    private ReservaEstadiaDTOResponse reservaEstadiaDTOResponse;
    private PasajeroDTOResponse pasajeroDTOResponse;
    private EmpleadoReservaDTOResponse empleadoReservaDTOResponse;

    private Double total;
    private String observacion;
    private Boolean pagada;

}
