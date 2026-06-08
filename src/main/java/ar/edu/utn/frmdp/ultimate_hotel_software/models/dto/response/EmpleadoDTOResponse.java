package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;

import java.time.LocalDate;

public class EmpleadoDTOResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String Email;
    private String telefono;
    private Turno turno;
    private RoleType roleType;
    private String usuario;
    private LocalDate fechaIngreso;
}
