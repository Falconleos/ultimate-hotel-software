package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.TipoHabitacion;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HabitacionDTOResponse {
    private Integer numero;

    private TipoHabitacion tipo;

    private Integer capacidad;

    private Double precioPorNoche;

    private EstadoHabitacion estado;
}
