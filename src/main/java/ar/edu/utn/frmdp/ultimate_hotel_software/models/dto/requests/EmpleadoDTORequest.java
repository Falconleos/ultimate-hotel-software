package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
@NoArgsConstructor
@Getter
public class EmpleadoDTORequest {

    @Valid //Valida elementos internos
    @NotNull
    private DatosPersonalesDTORequest datosPersonalesDTORequest;

    @NotNull
    private Turno turno;

    @NotNull
    private RoleType roleType;

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 3, max=50, message = "El usuario debe tener entre 3 y 50 caracteres")
    private String usuario;

    @NotBlank(message = "La constrasela es obligatoria")
    @Size(min = 3, max=50, message = "La contraseña debe tener entre 3 y 50 caracteres")
    private String password;

    Set<RoleType> roles;
}
