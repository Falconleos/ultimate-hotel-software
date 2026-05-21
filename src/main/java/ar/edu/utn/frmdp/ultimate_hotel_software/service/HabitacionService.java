package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Habitacion;

import java.util.List;

public interface HabitacionService {
    Habitacion findEntityById(Long id);
    List<Habitacion> findByEstadoHabitacion(EstadoHabitacion estadoHabitacion);
}
