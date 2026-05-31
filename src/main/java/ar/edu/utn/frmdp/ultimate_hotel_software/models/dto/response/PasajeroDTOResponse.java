package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PasajeroDTOResponse {

    private Long id;
    private DatosPersonalesDTOResponse datosPersonalesDTOResponse;
    private List<String> comentarios;
}
