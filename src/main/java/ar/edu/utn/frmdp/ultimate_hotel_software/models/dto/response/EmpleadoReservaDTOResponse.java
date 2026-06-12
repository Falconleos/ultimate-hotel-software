package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar informacion basica de los empleados")
public class EmpleadoReservaDTOResponse {
    @Schema(description = "Identificador unico del empleado",
            example = "1")
    private Long id;
    @Schema(description = "Nombre del empleado")
    private String nombre;
    @Schema(description = "Apellido del empleado")
    private String apellido;
    @Schema(description = "Turno laboral del empleado", example = "Mañana")
    private Turno turno;
    @Schema(description = "Rol del empleado dentro del hotel", example = "Administrativo, Limpieza, etc.")
    private RoleType roleType;
}
