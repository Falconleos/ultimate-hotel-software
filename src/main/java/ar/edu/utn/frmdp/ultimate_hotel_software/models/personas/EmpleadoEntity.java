package ar.edu.utn.frmdp.ultimate_hotel_software.models.personas;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Cargo;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity

public class EmpleadoEntity {

    @Embedded
    private DatosPersonalesEntity persona;
    private Turno turno;
    private Cargo cargo;
    private String usuario;
    private String password;
    private LocalDate fechaAlta;
}
