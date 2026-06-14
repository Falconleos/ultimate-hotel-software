package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO utlizado para implementar Spring Security en el logueo")
public class LoginRequest {

    @NotBlank
    @Schema(description = "Nombre de usuario")
    private String username;

    @NotBlank
    @Schema(description = "Contraseña del usuario")
    private String password;

}
