package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import java.time.LocalDateTime;

public class ErrorDTOResponse {

    private LocalDateTime timeStamp;
    private String mensaje;
    private String descripcion;

    public ErrorDTOResponse(String mensaje, String descripcion) {
        this.timeStamp = LocalDateTime.now();
        this.mensaje = mensaje;
        this.descripcion = descripcion;
    }

}
