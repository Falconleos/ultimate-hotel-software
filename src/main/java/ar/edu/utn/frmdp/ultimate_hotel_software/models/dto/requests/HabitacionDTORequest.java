package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;


import ar.edu.utn.frmdp.ultimate_hotel_software.enums.TipoHabitacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Schema(description = "DTO utilizado para crear habitaciones")

public class HabitacionDTORequest {
    @NotNull(message = "El numero de la habitacion es obligatorio")
    @Schema(description = "Numero de habitacion dentro del hotel", example = "203")
    private Integer numero;

    @NotNull(message = "El tipo de la habitacion es obligatorio")
    @Schema(description = "Tipo de habitacion", example = "Doble")
    private TipoHabitacion tipo;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser positiva")
    @Schema(description = "Cantidad de pasajeros por habitacion", example = "2 pasajeros")
    private Integer capacidad;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    @Schema(description = "Precio de la habitacion por noche", example = "USD$50")
    private Double precioPorNoche;
}
