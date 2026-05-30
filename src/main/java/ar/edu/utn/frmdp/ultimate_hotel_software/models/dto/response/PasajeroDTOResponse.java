package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.DatosPersonalesEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PasajeroDTOResponse {

   private Long id;
   private DatosPersonalesEntity persona;

}
