package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;


import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Habitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.HabitacionRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;

    @Override
    public Habitacion findEntityById(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow( ()->new RuntimeException() );
    }

    @Override
    public List<Habitacion> findByEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
        List<Habitacion>habitaciones;

        if(estadoHabitacion==null){
            habitaciones = habitacionRepository.findAll();
        }else{
            habitaciones = habitacionRepository.findByEstadoHabitacion(estadoHabitacion);
        }
        return habitaciones;
    }


}