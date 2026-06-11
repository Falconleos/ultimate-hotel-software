package ar.edu.utn.frmdp.ultimate_hotel_software.models;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "comentarios")

public class ComentarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String texto;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp //Hibernate gestiona automáticamente la fecha sin depender del ciclo de vida de JPA manualmente
    private LocalDateTime fechaComentario;

    @ManyToOne(optional = false) //Permite consultar comentarios por habitacion y comentarios por pasajero
    @JoinColumn(name = "estadia_id")
    private EstadiaEntity estadia;

}
