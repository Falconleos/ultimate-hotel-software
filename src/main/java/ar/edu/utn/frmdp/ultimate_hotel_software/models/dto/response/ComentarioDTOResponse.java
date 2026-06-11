package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComentarioDTOResponse {

    private Long id;
    private String texto;
    private LocalDateTime fechaComentario;
}
