package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.TipoHabitacion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO utilizado para mostrar habitaciones")
public class HabitacionDTOResponse {
    @Schema(description = "Identificador unico de la habitacion", example = "1")
    private Long id;
    @Schema(description = "Numero de habitacion dentro del hotel", example = "203")
    private Integer numero;
    @Schema(description = "Tipo de habitacion", example = "DOBLE",
            allowableValues = {"SIMPLE: Habitación individual", "DOBLE: Cama doble", "TRIPLE: Tres camas", "SUITE: Habitación Premium"})
    private TipoHabitacion tipo;
    @Schema(description = "Cantidad de pasajeros por habitacion", example = "2")
    private Integer capacidad;
    @Schema(description = "Precio de la habitacion por noche", example = "50.0")
    private Double precioPorNoche;
    @Schema(description = "Estado de la habitacion", example = "OCUPADA",
            allowableValues = {"DISPONIBLE, OCUPADA, MANTENIMIENTO"})
    private EstadoHabitacion estado;
}
