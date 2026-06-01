package ar.edu.utn.frmdp.ultimate_hotel_software.models.personas;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class DatosPersonalesEntity {

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    //Un empleado puede hospedarse como pasajero (unique es por tabla, no global)
    @Column(nullable = false, unique = true, length = 15)
    private String dni;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(length = 15)
    private String telefono;


}
