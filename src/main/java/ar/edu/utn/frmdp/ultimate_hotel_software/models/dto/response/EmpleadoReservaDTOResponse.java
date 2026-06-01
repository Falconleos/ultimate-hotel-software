package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Cargo;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.DatosPersonalesEntity;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmpleadoReservaDTOResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private Turno turno;
    private Cargo cargo;
}
