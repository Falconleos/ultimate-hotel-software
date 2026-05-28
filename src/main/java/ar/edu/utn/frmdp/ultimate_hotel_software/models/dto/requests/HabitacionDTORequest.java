package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;


import ar.edu.utn.frmdp.ultimate_hotel_software.enums.TipoHabitacion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data

public class HabitacionDTORequest {
    @NotNull(message = "El numero de la habitacion es obligatorio")
    private Integer numero;

    @NotNull(message = "El tipo de la habitacion es obligatorio")
    private TipoHabitacion tipo;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser positiva")
    private Integer capacidad;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    private Double precioPorNoche;
}
