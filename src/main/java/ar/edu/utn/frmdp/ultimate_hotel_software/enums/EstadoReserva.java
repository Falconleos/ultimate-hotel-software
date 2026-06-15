package ar.edu.utn.frmdp.ultimate_hotel_software.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estados de la ESTADIA en el sistema")
public enum EstadoReserva {

    @Schema(description = "Estadia pendiente de confirmacion")
    PENDIENTE,

    @Schema(description = "Estadia confirmada")
    CONFIRMADA,

    @Schema(description = "Estadia iniciada")
    INGRESADA,

    @Schema(description = "Estadia cancelada")
    CANCELADA,

    @Schema(description = "Estadia ausente")
    AUSENTE,

    @Schema(description = "Estadia interrumpida")
    INTERRUMPIDA,

    @Schema(description = "Estadia finalizada")
    CONCLUIDA;
}
