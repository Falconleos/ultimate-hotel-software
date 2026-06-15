package ar.edu.utn.frmdp.ultimate_hotel_software.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de HABITACIONES del sistema")
public enum TipoHabitacion {

    @Schema(description = "Habitacion simple")
    SIMPLE,

    @Schema(description = "Habitacion doble")
    DOBLE,

    @Schema(description = "Habitacion triple")
    TRIPLE,

    @Schema(description = "Suite")
    SUITE;
}
