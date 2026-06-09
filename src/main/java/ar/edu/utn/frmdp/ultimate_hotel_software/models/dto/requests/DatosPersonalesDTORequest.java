package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@Getter
public class DatosPersonalesDTORequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    private String apellido;

    @NotBlank(message = "El dni es obligatorio")
    @Pattern(
            regexp = "\\d{7,10}",
            message = "El dni debe contener entre 7 y 10 dígitos"
    )
    private String dni;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato valido")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;
}
