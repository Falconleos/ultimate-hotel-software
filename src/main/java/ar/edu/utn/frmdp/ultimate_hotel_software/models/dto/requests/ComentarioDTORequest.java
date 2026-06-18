package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Schema(description = "DTO utilizado para crear comentarios, los cuales representan la experiencia del pasajero durante su estadia. Solo contiene el texto del comentario y la estadia asociada")
public class ComentarioDTORequest {

    @NotBlank(message = "El comentario no puede estar vacio")
    @Size(max = 100, message = "El comentario debe tener menos de 100 caracteres")
    @Schema(description = "Contenido del comentario")
    private String texto;
    @Schema(description = "Identificador de la estadia asociada al comentario")
    private Long estadiaId;
}
