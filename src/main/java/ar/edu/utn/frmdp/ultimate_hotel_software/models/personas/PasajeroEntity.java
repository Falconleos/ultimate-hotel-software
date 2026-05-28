package ar.edu.utn.frmdp.ultimate_hotel_software.models.personas;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity

public class PasajeroEntity {
    @Embedded
    private DatosPersonalesEntity datosPersona;
    private List<String> comentarios;

}
