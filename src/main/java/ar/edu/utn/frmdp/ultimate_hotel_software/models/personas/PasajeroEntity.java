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
@Table(name = "pasajeros")

public class PasajeroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DatosPersonalesEntity datosPersona;

    @ElementCollection //Indica que el siguiente atributo es una coleccion de valores simples (String)
    @CollectionTable( //Crea una tabla de nombre pasajero_comentarios y la vincula con la tabla pasajeros mediante pasajero_id
            name = "pasajero_comentarios",
            joinColumns = @JoinColumn(name = "pasajero_id")
    )
    @Column(name = "comentario", length = 30)
    private List<String> comentarios;
}
