package ar.edu.utn.frmdp.ultimate_hotel_software.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de ROLES de los empleados del sistema")
public enum RoleType {

    @Schema(description = "Administrador del hotel")
    ADMINISTRATIVO,

    @Schema(description = "Recepcionista del hotel")
    RECEPCIONISTA,

    @Schema(description = "Auxiliar de limpieza del hotel")
    LIMPIEZA,

    @Schema(description = "Franquero del hotel")
    FRANQUERO,

    @Schema(description = "Auxiliar de mantenimiento del hotel")
    MANTENIMIENTO
}
