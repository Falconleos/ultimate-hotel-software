package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
@NoArgsConstructor
@Getter
@Schema(description = "DTO utilizado para crear empleados")
public class EmpleadoDTORequest {

    @Valid //Valida elementos internos
    @NotNull
    @Schema(description = "Datos de la persona", example = "Nombre, Apellido, Telefono")
    private DatosPersonalesDTORequest datosPersonalesDTORequest;

    @NotNull
    @Schema(description = "Turno laboral del empleado", example = "Mañana")
    private Turno turno;

    @NotNull
    @Schema(description = "Rol del empleado", example = "Limpieza")
    private RoleType roleType;

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 3, max=50, message = "El usuario debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre de usuario del empleado", example = "nordico94")
    private String usuario;

    @NotBlank(message = "La constraseña es obligatoria")
    @Size(min = 3, max=50, message = "La contraseña debe tener entre 3 y 50 caracteres")
    @Schema(description = "Contraseña del empleado")
    private String password;

    Set<RoleType> roles;
}
