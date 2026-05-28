package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.TipoHabitacion;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class HabitacionUpdateDTO {
    private TipoHabitacion tipo;

    @Positive(message = "La capacidad debe ser positiva")
    private Integer capacidad;

    @Positive(message = "El precio debe ser positivo")
    private Double precioPorNoche;

    private EstadoHabitacion estado;
}
