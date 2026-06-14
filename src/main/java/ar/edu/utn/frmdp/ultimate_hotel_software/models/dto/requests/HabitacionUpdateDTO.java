package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.TipoHabitacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Schema(description = "Informacion necesaria para modificar una habitacion")
public class HabitacionUpdateDTO {
    @Schema(description = "Tipo de habitacion dentro del hotel", example = "DOBLE")
    private TipoHabitacion tipo;

    @Positive(message = "La capacidad debe ser positiva")
    @Schema(description = "Cantidad de pasajeros por habitacion", example = "2 pasajeros")
    private Integer capacidad;

    @Positive(message = "El precio debe ser positivo")
    @Schema(description = "Precio de la habitacion por noche", example = "USD$50")
    private Double precioPorNoche;

    @Schema(description = "Estado de la habitacion", example = "Ocupado")
    private EstadoHabitacion estado;
}
