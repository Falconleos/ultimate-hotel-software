package ar.edu.utn.frmdp.ultimate_hotel_software.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de TURNO LABORAL de los empleados del sistema")
public enum Turno {

    @Schema(description = "Turno mañana")
    MANANA,

    @Schema(description = "Turno tarde")
    TARDE,

    @Schema(description = "Turno noche")
    NOCHE
}
