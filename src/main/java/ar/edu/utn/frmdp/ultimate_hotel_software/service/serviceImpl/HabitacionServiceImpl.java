package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;


import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.*;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.HabitacionMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionUpdateDTO;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.EstadiaRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.HabitacionRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.ReservaRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    private final ReservaRepository reservaRepository;

    //1. Busquedas por ID
    //1.1. Devuelve entidad
    @Override
    public HabitacionEntity findEntityById(Long id) {

        return habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new HabitacionNoEncontradaException("Habitación no encontrada"));
    }

    //1.2. Devuelve DTO Response
    @Override
    public HabitacionDTOResponse findById(Long id) {

        HabitacionEntity habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new HabitacionNoEncontradaException("Habitación no encontrada"));

        return habitacionMapper.toResponse(habitacion);
    }

    //2. Listar habitaciones
    //2.1. Listar entidades
    @Override
    public List<HabitacionEntity> findAll() {
        return habitacionRepository.findAll();
    }

    //2.2. Listar DTOs Response
    @Override
    public List<HabitacionDTOResponse> getAll() {
        return habitacionRepository.findAll().stream()
                .map(habitacionMapper::toResponse)
                .toList();
    }


    //3. Crear habitacion
    @Override
    public HabitacionDTOResponse save(HabitacionDTORequest dto) {

        if (habitacionRepository.findByNumero(dto.getNumero()).isPresent()) {
            throw new HabitacionDuplicadaException("Ya existe una habitación con ese número");
        }

        HabitacionEntity habitacion = habitacionMapper.toEntity(dto);

        habitacion.setEstado(EstadoHabitacion.DISPONIBLE);

        HabitacionEntity guardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(guardada);
    }


    //4. Borrar habitacion
    @Override
    public void delete(Long id) {

        HabitacionEntity habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new HabitacionNoEncontradaException("Habitación no encontrada"));
        if (habitacion.getEstado() == EstadoHabitacion.OCUPADA) {
            throw new HabitacionEnUsoException("No se puede eliminar una habitación ocupada");
        }

        //Falta validar que no haya reservas en la habitacion que se quiere borrar!!
        if (reservaRepository.existsByHabitacionEntity_Id(id)) {
            throw new HabitacionEnUsoException(
                    "No se puede eliminar la habitación: existen reservas asociadas"
            );
        }

        habitacionRepository.delete(habitacion);
    }

    //5. Actualizaciones
    // 5.1. Actualizar habitacion (completo)
    @Override
    public HabitacionDTOResponse update(Long id, HabitacionUpdateDTO dto) {

        HabitacionEntity habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new HabitacionNoEncontradaException("Habitación no encontrada"));

        habitacionMapper.updateHabitacionFromDto(dto, habitacion);

        HabitacionEntity actualizada = habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(actualizada);
    }

    //5.2. Actualizar en repositorio
    @Override
    public void updateHabitacion(HabitacionEntity habitacionEntity){
        habitacionRepository.save(habitacionEntity);
    }

    //5.3. Actualizar estado a MANTENIMIENTO
    @Override
    public HabitacionDTOResponse realizarMantenimiento(Long id) {

        HabitacionEntity habitacion = habitacionRepository.findById(id)
                .orElseThrow(() ->
                        new HabitacionNoEncontradaException("Habitación no encontrada"));

        if (habitacion.getEstado() == EstadoHabitacion.MANTENIMIENTO) {
            throw new HabitacionYaEnMantenimientoException("La habitacion ya se encuentra en mantenimiento");
        }

        habitacion.setEstado(EstadoHabitacion.MANTENIMIENTO);

        HabitacionEntity actualizada =
                habitacionRepository.save(habitacion);

        return habitacionMapper.toResponse(actualizada);
    }

    //6. Otras busquedas y listados
    //6.1. Listar habitacioens disponibles
    @Override
    public List<HabitacionDTOResponse> habitacionesDisponibles() {

        return habitacionRepository
                .findByEstado(EstadoHabitacion.DISPONIBLE)
                .stream()
                .map(habitacionMapper::toResponse)
                .toList();
    }

    //6.2. Listar habitaciones por estado
    @Override
    public List<HabitacionEntity> findByEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
        return habitacionRepository.findByEstado(estadoHabitacion);
    }

    //7. Calculos
    //7.1. Determinar cantidad de habitaciones
    @Override
    public Integer cantidadHabitaciones() {
        return habitacionRepository.findAll().size();
    }
}