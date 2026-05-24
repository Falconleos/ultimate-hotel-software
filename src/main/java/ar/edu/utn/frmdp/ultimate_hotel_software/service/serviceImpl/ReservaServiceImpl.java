package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.ReservaMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Habitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Reserva;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.Empleado;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.ReservaRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.HabitacionService;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    private final HabitacionService habitacionService;
    private final EmpleadoService empleadoService;


    @Override
    public List<ReservaDTOResponse> listar(Boolean activa) {

        List<Reserva>reservas;
        if(activa==null){
            reservas=reservaRepository.findAll();
        }else{
            reservas=reservaRepository.findByActiva(activa);
        }
        return reservas.stream()
                .map(reservaMapper::toDto)
                .toList();
    }

    @Override
    public ReservaDTOResponse crearReserva(ReservaDTORequest request) {

        if (!request.getCheckOut().isAfter(request.getCheckIn())) {
            throw new IllegalArgumentException("La fecha de check-out debe ser posterior a la de check-in");
        }

        Empleado empleado = empleadoService.findEntityById(request.getEmpleado_id());
        Habitacion habitacion = habitacionService.findEntityById(request.getHabitacion_id());

        if(! habitacionesDisponibles(request.getCheckIn(),request.getCheckOut(),request.getCantidadPax()).contains(habitacion)){
            throw new RuntimeException("habitacion no disponible");
        }

        Reserva reserva = reservaMapper.toEntity(request);
            reserva.setEmpleado(empleado);
            reserva.setHabitacion(habitacion);

            reservaRepository.save(reserva);

        return reservaMapper.toDto(reserva);
    }

    @Override
    public List<Habitacion> habitacionesDisponibles(LocalDate checkIn, LocalDate checkOut, Integer pax) {

        List<Habitacion> habitacionesOcupadas = reservaRepository.findByActiva(true).stream()
                .filter(r -> checkOut.isAfter(r.getCheckIn()) && checkIn.isBefore(r.getCheckOut()))
                .map(Reserva::getHabitacion)
                .toList();//encuentra las habitaciones con reservas en ese rango de fecha

        return habitacionService.findByEstadoHabitacion(null).stream()//devuelve todas las habitaciones
                .filter(h -> h.getCapacidad() >= pax)//filtra solo donde haya capacidad
                .filter(h -> !habitacionesOcupadas.contains(h))//quita las habitaciones ocupadas
                .sorted(Comparator.comparingDouble(Habitacion::getPrecioPorNoche))//las ordena de menor a mayor por precio
                .toList();
    }

}
