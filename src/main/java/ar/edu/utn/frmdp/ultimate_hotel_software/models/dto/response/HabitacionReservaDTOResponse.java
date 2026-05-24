package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.TipoHabitacion;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HabitacionReservaDTOResponse {

    private Integer numero;
    private TipoHabitacion tipoHabitacion;

}
