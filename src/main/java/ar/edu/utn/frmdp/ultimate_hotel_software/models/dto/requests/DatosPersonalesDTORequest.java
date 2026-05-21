package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DatosPersonalesDTORequest {

    private String nombre;
    private String apellido;
    private String dni;
    private String email;

    private String telefono;
}
