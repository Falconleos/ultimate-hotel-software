package ar.edu.utn.frmdp.ultimate_hotel_software.models.personas;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.ComentarioEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    // REEMPLAZÁ ESTE BLOQUE EN PasajeroEntity.java
    @OneToMany(
            mappedBy = "pasajeroEntity", // <-- Cambiado de "pasajero" a "pasajeroEntity"
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EstadiaEntity> estadias = new ArrayList<>();
}
