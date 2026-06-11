package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar datos personales")
public class DatosPersonalesDTOResponse {
    @Schema(description = "Nombre del la persona")
    private String nombre;
    @Schema(description = "Apellido del la persona")
    private String apellido;
    @Schema(description = "DNI del la persona")
    private String dni;
    @Schema(description = "Email del la persona")
    private String email;
    @Schema(description = "Telefono del la persona")
    private String telefono;
}
