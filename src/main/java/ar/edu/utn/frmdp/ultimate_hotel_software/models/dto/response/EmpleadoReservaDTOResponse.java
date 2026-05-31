package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Cargo;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmpleadoReservaDTOResponse {

    private Long id;
    private DatosPersonalesDTOResponse datosPersonalesDTOResponse;
    private Turno turno;
    private Cargo cargo;
    private String usuario;
    private LocalDate fechaIngreso;
    private boolean activo;
}
