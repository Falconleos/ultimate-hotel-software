package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Habitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionUpdateDTO;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;

import java.util.List;

public interface HabitacionService {
    Habitacion findEntityById(Long id);

    List<Habitacion> findByEstadoHabitacion(EstadoHabitacion estadoHabitacion);

    HabitacionDTOResponse save(HabitacionDTORequest dto);

    HabitacionDTOResponse findById(Long id);

    void delete(Long id);

    HabitacionDTOResponse update(Long id, HabitacionUpdateDTO dto);

    HabitacionDTOResponse realizarMantenimiento(Long id);

    List<HabitacionDTOResponse> habitacionesDisponibles();

}
