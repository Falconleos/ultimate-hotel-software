package ar.edu.utn.frmdp.ultimate_hotel_software.models;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoEstadia;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class EstadiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "reserva_id")
    private ReservaEntity reserva;

    private EstadoEstadia estado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pasajero_id")
    private PasajeroEntity pasajeroEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id")
    private EmpleadoEntity empleado;

    private Double total;
    private Boolean pagada;
    private Boolean activa;

    @OneToMany(mappedBy = "estadia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioEntity>comentarios;

    @PrePersist
    public void OnCreate(){
        estado=EstadoEstadia.EN_CURSO;
        activa=true;
    }

}
