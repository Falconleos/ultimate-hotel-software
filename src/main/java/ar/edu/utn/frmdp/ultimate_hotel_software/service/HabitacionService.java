package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionUpdateDTO;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HabitacionService {
    HabitacionEntity findEntityById(Long id);

    List<HabitacionDTOResponse> getAll();

    List<HabitacionEntity> findByEstadoHabitacion(EstadoHabitacion estadoHabitacion);

    void updateHabitacion(HabitacionEntity habitacionEntity);

    HabitacionDTOResponse save(HabitacionDTORequest dto);

    HabitacionDTOResponse findById(Long id);

    void delete(Long id);

    HabitacionDTOResponse update(Long id, HabitacionUpdateDTO dto);

    HabitacionDTOResponse realizarMantenimiento(Long id);

    List<HabitacionDTOResponse> habitacionesDisponibles();

    Integer cantidadHabitaciones();

    List<HabitacionEntity>findAll();

}
