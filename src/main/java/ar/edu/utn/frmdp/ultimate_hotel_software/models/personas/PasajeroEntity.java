package ar.edu.utn.frmdp.ultimate_hotel_software.models.personas;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity

public class PasajeroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DatosPersonalesEntity datosPersona;

    @ElementCollection
    @CollectionTable(
            name = "pasajero_comentarios",
            joinColumns = @JoinColumn(name = "pasajero_id")
    )
    @Column(name = "comentario")
    private List<String> comentarios;

}
