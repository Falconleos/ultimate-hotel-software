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
    @Schema(description = "Tipo de habitacion", example = "Doble")
    private TipoHabitacion tipo;
    @Schema(description = "Cantidad de pasajeros por habitacion", example = "2 pasajeros")
    private Integer capacidad;
    @Schema(description = "Precio de la habitacion por noche", example = "USD$50")
    private Double precioPorNoche;
    @Schema(description = "Estado de la habitacion", example = "Ocupado")
    private EstadoHabitacion estado;
}
