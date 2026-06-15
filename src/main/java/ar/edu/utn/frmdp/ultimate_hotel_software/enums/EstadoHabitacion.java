package ar.edu.utn.frmdp.ultimate_hotel_software.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estados de la HABITACION en el sistema")
public enum EstadoHabitacion {
    @Schema(description = "Habitacion disponible")
    DISPONIBLE,

    @Schema(description = "Habitacion ocupada")
    OCUPADA,

    @Schema(description = "Habitacion en mantenimiento")
    MANTENIMIENTO;
}
