package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar informacion de los errores")
@Getter
public class ErrorDTOResponse {
    @Schema(description = "Sello de tiempo del error")
    private LocalDateTime timeStamp;
    @Schema(description = "Mensaje de error")
    private String mensaje;
    @Schema(description = "Descripcion del error")
    private String descripcion;

    public ErrorDTOResponse(String mensaje, String descripcion) {
        this.timeStamp = LocalDateTime.now();
        this.mensaje = mensaje;
        this.descripcion = descripcion;
    }

}
