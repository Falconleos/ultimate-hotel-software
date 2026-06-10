package ar.edu.utn.frmdp.ultimate_hotel_software.models;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "cancelaciones_reserva")
public class CancelacionReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(optional = false)
    @JoinColumn(name = "reserva_id", referencedColumnName = "id")
    private ReservaEntity reservaEntity;

    private LocalDateTime fecha;
    private String motivo;

    @PrePersist
    public void onCreate(){
        if(motivo.isBlank()){
            motivo = "no especificado";
        }
    }

}
