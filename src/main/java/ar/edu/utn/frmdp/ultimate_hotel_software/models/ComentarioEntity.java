package ar.edu.utn.frmdp.ultimate_hotel_software.models;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @Column(nullable = false, length = 50)
    private String autor;

    @Column(nullable = false, updatable = false)
    private LocalDate fechaComentario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pasajero_id")
    private PasajeroEntity pasajeroEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "habitacion_id")
    private Habitacion habitacion;

    @PrePersist
    public void prePersist() {
        this.fechaComentario = LocalDate.now();
    }
}
