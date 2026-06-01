package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ComentarioDTORequest {

    @NotBlank(message = "El comentario no puede estar vacio")
    @Size(max = 100, message = "El comentario debe tener menos de 100 caracteres")
    private String texto;
}
