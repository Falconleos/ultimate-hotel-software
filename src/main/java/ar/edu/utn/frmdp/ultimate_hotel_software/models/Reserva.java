package ar.edu.utn.frmdp.ultimate_hotel_software.models;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoReserva;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.Empleado;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Reserva {

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
    private String comentario;
    private Boolean activa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "habitacion_id")
    private Habitacion habitacion;

    @PrePersist
    public void onCreate(){
        if(comentario==null || comentario.isBlank()){
            comentario = "sin comentarios";
        }
        activa=true;
    }

}
