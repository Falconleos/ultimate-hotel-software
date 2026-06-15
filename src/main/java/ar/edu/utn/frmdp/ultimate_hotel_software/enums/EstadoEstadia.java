package ar.edu.utn.frmdp.ultimate_hotel_software.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de estado de la ESTADIA en el sistema")
public enum EstadoEstadia {
    @Schema(description = "Estadia en curso")
    EN_CURSO,

    @Schema(description = "Estadia completada")
    COMPLETADA,

    @Schema(description = "Estadia interrumpida")
    INTERRUMPIDA;
}
