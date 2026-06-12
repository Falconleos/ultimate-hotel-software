package ar.edu.utn.frmdp.ultimate_hotel_software.models;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoReserva;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer cantidadPax;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;

    private String nombre;
    private String apellido;
    private String telefono;
    private String observacion;
    private Boolean activa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id")
    private EmpleadoEntity empleadoEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "habitacion_id")
    private HabitacionEntity habitacionEntity;

    private Double total;


    @OneToOne(mappedBy = "reservaEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CancelacionReservaEntity cancelacion;

    @PrePersist
    public void onCreate(){
        if(observacion==null || observacion.isBlank()){
            observacion = "sin comentarios";
        }
        activa=true;
    }

}
