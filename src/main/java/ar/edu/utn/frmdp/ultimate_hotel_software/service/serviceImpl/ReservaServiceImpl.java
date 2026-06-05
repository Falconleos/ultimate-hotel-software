package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoReserva;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.ConflictoDeEstadoException;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.FechaInvalidaException;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.HabitacionNoDisponibleException;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.InvalidIdException;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.CancelacionReservaMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.ReservaMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.CancelacionReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.ReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.CancelacionReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.CancelacionReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.ReservaRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.HabitacionService;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.ReservaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;

    private final ReservaMapper reservaMapper;
    private final CancelacionReservaMapper cancelacionReservaMapper;

    private final CancelacionReservaServiceImpl cancelacionReservaService;
    private final HabitacionService habitacionService;
    private final EmpleadoService empleadoService;


    @Override
    public List<ReservaDTOResponse> listar(Boolean activa) {
        List<ReservaEntity>reservas;

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
    @Transactional
    public ReservaDTOResponse crearReserva(ReservaDTORequest request) {

        if (!request.getCheckOut().isAfter(request.getCheckIn())) {
            throw new FechaInvalidaException("La fecha de check-out debe ser posterior a la de check-in");
        }

        EmpleadoEntity empleadoEntity = empleadoService.findEntityById(request.getEmpleadoId());
        HabitacionEntity habitacion = habitacionService.findEntityById(request.getHabitacionId());

        if(!habitacionesDisponibles(request.getCheckIn(),request.getCheckOut(),request.getCantidadPax()).contains(habitacion)){
            throw new HabitacionNoDisponibleException("habitacion no disponible");
        }

        ReservaEntity reserva = reservaMapper.toEntity(request);
            reserva.setEmpleadoEntity(empleadoEntity);
            reserva.setHabitacionEntity(habitacion);
            reserva.setActiva(true);
            reserva.setEstadoReserva(EstadoReserva.PENDIENTE);

        ReservaEntity reservaAlmacenada = reservaRepository.save(reserva);

        return reservaMapper.toDto(reservaAlmacenada);
    }

    @Override
    public ReservaEntity findEntityById(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow( ()->new InvalidIdException("Id de reserva invalido"));
    }

    @Override
    public List<HabitacionEntity> habitacionesDisponibles(LocalDate checkIn, LocalDate checkOut, Integer pax) {
        List<HabitacionEntity> habitacionesOcupadas = reservaRepository.findByActiva(true).stream()
                .filter(r -> checkOut.isAfter(r.getCheckIn()) && checkIn.isBefore(r.getCheckOut()))
                .map(ReservaEntity::getHabitacionEntity)
                .toList();//encuentra las habitaciones con reservas en ese rango de fecha

        return habitacionService.findByEstadoHabitacion(null).stream()//devuelve todas las habitaciones activas
                .filter(h -> h.getCapacidad() >= pax)//filtra solo donde haya capacidad
                .filter(h -> !habitacionesOcupadas.contains(h))//quita las habitaciones ocupadas
                .sorted(Comparator.comparingDouble(HabitacionEntity::getPrecioPorNoche))//las ordena de menor a mayor por precio
                .toList();
    }

    @Override
    public void update(ReservaEntity reserva) {
        reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public void confirmarReserva(Long id) {
        ReservaEntity reserva = findEntityById(id);
        if(reserva.getEstadoReserva() != EstadoReserva.PENDIENTE){
            throw new ConflictoDeEstadoException("No se puede confirmar ya que la reserva está " + reserva.getEstadoReserva());
        }
        reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
        reservaRepository.save(reserva);
    }

    @Override
    public CancelacionReservaDTOResponse cancelarReserva(CancelacionReservaDTORequest request) {

        ReservaEntity reserva = findEntityById(request.getReserva_id());

        if (reserva.getEstadoReserva() == EstadoReserva.INGRESADA) {
            throw new ConflictoDeEstadoException("La reserva ya fue ingresada, solo puede interrumpir la estadía.");
        }
        if (reserva.getEstadoReserva() == EstadoReserva.AUSENTE) {
            throw new ConflictoDeEstadoException("No hace falta cancelar, ya se encuentra inactiva por ausencia.");
        }
        if (reserva.getEstadoReserva() == EstadoReserva.CONCLUIDA) {
            throw new ConflictoDeEstadoException("Al concluir la estadía la reserva ya no está activa.");
        }
        if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            throw new ConflictoDeEstadoException("La reserva ya se encuentra cancelada.");
        }

        reserva.setEstadoReserva(EstadoReserva.CANCELADA);
        reserva.setActiva(false);
        reservaRepository.save(reserva);

        CancelacionReservaEntity cancelacion = new CancelacionReservaEntity();
            cancelacion.setReservaEntity(reserva);
            cancelacion.setMotivo(request.getMotivo());

        CancelacionReservaEntity cancelacionGuardada = cancelacionReservaService.crear(cancelacion);

        return cancelacionReservaMapper.toDto(cancelacionGuardada);
    }

    @Override
    public void procesarAusenciaDeReservas() {
        LocalDate hoy = LocalDate.now();
        List<ReservaEntity>ausencias = reservaRepository.findByActiva(true).stream()
                .filter(r -> r.getCheckIn().isBefore(hoy))
                .filter(r -> r.getEstadoReserva() == EstadoReserva.PENDIENTE ||
                                          r.getEstadoReserva() == EstadoReserva.CONFIRMADA)
                .toList();

        for(ReservaEntity reserva : ausencias){
            reserva.setEstadoReserva(EstadoReserva.AUSENTE);
            reserva.setActiva(false);
            reservaRepository.save(reserva);
        }
    }
}
