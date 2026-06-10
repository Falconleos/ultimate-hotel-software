package ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@Getter
public class ReservaDTORequest {

    @FutureOrPresent(message = "El check in no puede ser pasada")
    private LocalDate checkIn;
    @FutureOrPresent(message = "El check out no puede ser pasado")
    private LocalDate checkOut;
    @Min(value = 1,message = "el valor minimo debe ser 1")
    private Integer cantidadPax;
    @NotBlank
    @Size(min = 2, max = 100, message = "valor mínimo 2 caracteres y máximo 100")
    private String nombre;
    @Size(min = 2, max = 100, message = "valor mínimo 2 caracteres y máximo 100")
    private String apellido;
    @NotBlank
    private String telefono;
    private String observacion;

    @NotNull
    private Long empleadoId;
    @NotNull
    private Long habitacionId;

}
