package ar.edu.utn.frmdp.ultimate_hotel_software.models;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoEstadia;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer cantidadPax;
    private EstadoEstadia estado;
    private String comentario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pasajero_id")
    private PasajeroEntity pasajeroEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id")
    private EmpleadoEntity empleado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "habitacion_id")
    private HabitacionEntity habitacion;

    private Double total;
    private Boolean pagada;
    private Boolean activa;

    @PrePersist
    public void OnCreate(){
        if(comentario.isBlank()){
            comentario="sin comentarios";
        }
        estado=EstadoEstadia.EN_CURSO;
        activa=true;
    }

}
