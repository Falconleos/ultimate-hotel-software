package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(description = "DTO utilizado para crear datos personales de las personas")
public class DatosPersonalesDTORequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre del la persona")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    @Schema(description = "Apellido del la persona")
    private String apellido;

    @NotBlank(message = "El dni es obligatorio")
    @Pattern(
            regexp = "\\d{7,10}",
            message = "El dni debe contener entre 7 y 10 dígitos"
    )
    @Schema(description = "DNI del la persona")
    private String dni;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato valido")
    @Schema(description = "Email del la persona")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "El telefono debe contener 10 digitos numericos"
    )
    @Schema(
            description = "Telefono de la persona",
            example = "2235123456"
    )
    private String telefono;
}
