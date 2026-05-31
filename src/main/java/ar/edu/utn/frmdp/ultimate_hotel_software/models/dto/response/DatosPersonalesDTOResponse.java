package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DatosPersonalesDTOResponse {
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;
}
