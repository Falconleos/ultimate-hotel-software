package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;


import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.InvalidIdException;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.HabitacionMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionUpdateDTO;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.HabitacionRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    @Override
    public HabitacionDTOResponse save(HabitacionDTORequest dto) {

        if (habitacionRepository.findByNumero(dto.getNumero()).isPresent()) {
            log.warn("Habitacion duplicada con el numero {}", dto.getNumero() );
            throw new InvalidIdException("Ya existe una habitación con ese número");
        }

        HabitacionEntity habitacion = habitacionMapper.toEntity(dto);

        habitacion.setEstado(EstadoHabitacion.DISPONIBLE);

        HabitacionEntity guardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(guardada);
    }

    @Override
    public HabitacionDTOResponse findById(Long id) {

        HabitacionEntity habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidIdException("Habitación no encontrada"));

        return habitacionMapper.toResponse(habitacion);
    }

    @Override
    public void delete(Long id) {

        HabitacionEntity habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidIdException("Habitación no encontrada"));

        habitacionRepository.delete(habitacion);
    }

    @Override
    public HabitacionDTOResponse update(Long id, HabitacionUpdateDTO dto) {

        HabitacionEntity habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidIdException("Habitación no encontrada"));

        habitacionMapper.updateHabitacionFromDto(dto, habitacion);

        HabitacionEntity actualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(actualizada);
    }

    //update
    @Override
    public void updateHabitacion(HabitacionEntity habitacionEntity){
        habitacionRepository.save(habitacionEntity);
    }

    @Override
    public HabitacionDTOResponse realizarMantenimiento(Long id) {

        HabitacionEntity habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidIdException("Habitación no encontrada"));

        habitacion.setEstado(EstadoHabitacion.MANTENIMIENTO);

        HabitacionEntity actualizada =
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
    public HabitacionEntity findEntityById(Long id) {

        return habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidIdException("id de habitacion invalido"));
    }

    @Override
    public List<HabitacionEntity> findByEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
        return habitacionRepository.findByEstado(estadoHabitacion);
    }

    @Override
    public List<HabitacionEntity> findAll() {
        return habitacionRepository.findAll();
    }

    @Override
    public Integer cantidadHabitaciones() {
        return habitacionRepository.findAll().size();
    }
}