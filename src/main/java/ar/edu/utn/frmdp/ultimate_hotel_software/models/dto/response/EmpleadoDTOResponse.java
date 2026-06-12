package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar empleados")
public class EmpleadoDTOResponse {

    @Schema(description = "Identificador unico del empleado",
    example = "1")
    private Long id;

    @Schema(description = "Nombre del empleado")
    private String nombre;

    @Schema(description = "Apellido del empleado")
    private String apellido;
    @Schema(description = "DNI del empleado")
    private String dni;
    @Schema(description = "Email del empleado")
    private String email;
    @Schema(description = "Telefono del empleado")
    private String telefono;

    @Schema(description = "Turno laboral del empleado", example = "Mañana")
    private Turno turno;

    @Schema(description = "Rol del empleado dentro del hotel", example = "Administrativo, Limpieza, etc.")
    private RoleType roleType;

    @Schema(description = "Nombre de usuario del empleado")
    private String usuario;
    @Schema(description = "Fecha de ingreso del empleado")
    private LocalDate fechaIngreso;
}
