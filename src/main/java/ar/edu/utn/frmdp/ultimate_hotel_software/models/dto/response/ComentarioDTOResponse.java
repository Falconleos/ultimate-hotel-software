package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar comentarios")
public class ComentarioDTOResponse {

    @Schema(description = "Identificador unico del comentario",
            example = "1")
    private Long id;
    @Schema(description = "Contenido del comentario")
    private String texto;
    @Schema(description = "Fecha de realizacion del comentario")
    private LocalDateTime fechaComentario;
}
