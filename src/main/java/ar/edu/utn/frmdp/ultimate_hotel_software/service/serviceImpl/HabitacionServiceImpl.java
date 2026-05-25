package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;


import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.HabitacionMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Habitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionUpdateDTO;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.HabitacionRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    @Override
    public HabitacionDTOResponse save(HabitacionDTORequest dto) {

        if (habitacionRepository.findByNumero(dto.getNumero()).isPresent()) {
            throw new RuntimeException("Ya existe una habitación con ese número");
        }

        Habitacion habitacion = habitacionMapper.toEntity(dto);

        habitacion.setEstado(EstadoHabitacion.DISPONIBLE);

        Habitacion guardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(guardada);
    }

    @Override
    public HabitacionDTOResponse findById(Long id) {

        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Habitación no encontrada"));

        return habitacionMapper.toResponse(habitacion);
    }

    @Override
    public void delete(Long id) {

        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Habitación no encontrada"));

        habitacionRepository.delete(habitacion);
    }

    @Override
    public HabitacionDTOResponse update(Long id, HabitacionUpdateDTO dto) {

        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Habitación no encontrada"));

        habitacionMapper.updateHabitacionFromDto(dto, habitacion);

        Habitacion actualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(actualizada);
    }

    @Override
    public HabitacionDTOResponse realizarMantenimiento(Long id) {

        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Habitación no encontrada"));

        habitacion.setEstado(EstadoHabitacion.MANTENIMIENTO);

        Habitacion actualizada =
                habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(actualizada);
    }

    @Override
    public List<HabitacionDTOResponse> habitacionesDisponibles() {

        return habitacionRepository
                .findByEstado(EstadoHabitacion.DISPONIBLE)
                .stream()
                .map(habitacionMapper::toResponse)
                .toList();
    }

    @Override
    public Habitacion findEntityById(Long id) {

        return habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Habitación no encontrada"));
    }

    @Override
    public List<Habitacion> findByEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
        return habitacionRepository.findByEstado(estadoHabitacion);
    }
}