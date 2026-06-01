package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Cargo;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.DatosPersonalesEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public class EmpleadoDTOResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String Email;
    private String telefono;
    private Turno turno;
    private Cargo cargo;
    private String usuario;
    private LocalDate fechaIngreso;
}
