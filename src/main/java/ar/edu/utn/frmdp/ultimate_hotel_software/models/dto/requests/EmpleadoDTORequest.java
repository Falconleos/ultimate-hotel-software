package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Cargo;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import lombok.Getter;

@Getter
public class EmpleadoDTORequest {

    private DatosPersonalesDTORequest datosPersonalesDTORequest;
    private Turno turno;
    private Cargo cargo;
    private String usuario;
    private String password;
}
