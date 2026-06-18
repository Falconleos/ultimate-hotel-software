package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoReserva;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.*;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.CancelacionReservaMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.HabitacionMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.ReservaMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.CancelacionReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.ReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.CancelacionReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.CancelacionReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
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
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    private final CancelacionReservaMapper cancelacionReservaMapper;
    private final CancelacionReservaServiceImpl cancelacionReservaService;

    private final HabitacionService habitacionService;
    private final HabitacionMapper habitacionMapper;

    private final EmpleadoService empleadoService;



    //1. Busqueda por ID
    //1.1. Devuelve entidad
    @Override
    public ReservaEntity findEntityById(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow( ()->new ReservaNoEncontradaException("Reserva no encontrada"));
    }

    //1.2. Devuelve DTOResponse
    @Override
    public ReservaDTOResponse findById(Long id) {
        return reservaMapper.toDto(findEntityById(id));
    }

    //2. Listar reservas activas y listar todas las reservas
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

    //3. Crear reserva
    @Override
    @Transactional
    public ReservaDTOResponse crearReserva(ReservaDTORequest request) {

        if (!request.getCheckOut().isAfter(request.getCheckIn())) {
            throw new FechaInvalidaException("La fecha de check-out debe ser posterior a la de check-in");
        }

        EmpleadoEntity empleadoEntity = empleadoService.findEntityById(request.getEmpleadoId());
        HabitacionEntity habitacion = habitacionService.findEntityById(request.getHabitacionId());

        if (habitacion.getCapacidad()< request.getCantidadPax()){
            throw new CapacidadExcedidaException("capacidad de habitacion excedida");
        }

        if(!habitacionesDisponibles(request.getCheckIn(),request.getCheckOut(),request.getCantidadPax()).contains(habitacion)){
            throw new HabitacionNoDisponibleException("habitacion no disponible");
        }

        Long cantDias = ChronoUnit.DAYS.between(request.getCheckIn(),request.getCheckOut());
        Double total = habitacion.getPrecioPorNoche() * cantDias;

        ReservaEntity reserva = reservaMapper.toEntity(request);
            reserva.setEmpleadoEntity(empleadoEntity);
            reserva.setHabitacionEntity(habitacion);
            reserva.setTotal(total);
            reserva.setActiva(true);
            reserva.setEstadoReserva(EstadoReserva.PENDIENTE);

        ReservaEntity reservaAlmacenada = reservaRepository.save(reserva);

        return reservaMapper.toDto(reservaAlmacenada);
    }

    //3.2. Crear cancelacion de reserva
    @Override
    @Transactional
    public CancelacionReservaDTOResponse cancelarReserva( CancelacionReservaDTORequest request) {

        //1. Busco reserva existente
        ReservaEntity reserva = findEntityById(request.getReserva_id());

        //2. Valido estado de reserva existente
        if (reserva.getEstadoReserva() == EstadoReserva.INGRESADA) {
            throw new ConflictoDeEstadoReservaException("La reserva ya fue ingresada, solo puede interrumpir la estadía.");
        }
        if (reserva.getEstadoReserva() == EstadoReserva.AUSENTE) {
            throw new ConflictoDeEstadoReservaException("No hace falta cancelar, ya se encuentra inactiva por ausencia.");
        }
        if (reserva.getEstadoReserva() == EstadoReserva.CONCLUIDA) {
            throw new ConflictoDeEstadoReservaException("No se puede cancelar. Al concluir la estadía la reserva ya no está activa.");
        }
        if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            throw new ConflictoDeEstadoReservaException("No se puede cancelar. La reserva ya se encuentra cancelada.");
        }

        //3. Actualizo estado de reserva existente
        reserva.setEstadoReserva(EstadoReserva.CANCELADA);
        reserva.setActiva(false);
        reservaRepository.save(reserva);

        //4. Crear cancelacion de reserva
        CancelacionReservaEntity cancelacion = new CancelacionReservaEntity();
        cancelacion.setReservaEntity(reserva);
        cancelacion.setMotivo(request.getMotivo());

        CancelacionReservaEntity cancelacionGuardada = cancelacionReservaService.crear(cancelacion);

        return cancelacionReservaMapper.toDto(cancelacionGuardada);
    }


    //5. Actualizar reserva
    //5.1. Actualizar reserva completa en repositorio
    @Override
    public void update(ReservaEntity reserva) {
        reservaRepository.save(reserva);
    }

    //5.2. Actualizar estado de reserva de PENDIENTE a CONFIRMADA
    @Override
    @Transactional
    public ReservaDTOResponse confirmarReserva(Long id) {
        ReservaEntity reserva = findEntityById(id);
        if(reserva.getEstadoReserva() != EstadoReserva.PENDIENTE){
            throw new ConflictoDeEstadoReservaException("Estado actual de reserva: " + reserva.getEstadoReserva() + ". Para confirmar el estado debe ser PENDIENTE");
        }
        reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
        ReservaDTOResponse reservaDTOResponse =  reservaMapper.toDto(reservaRepository.save(reserva));
        return reservaDTOResponse;
    }

    //6. Otras busquedas y listados
    //6.1.1. Listar entidades de habitaciones disponibles segun fechas de CHECK-IN, CHECK-OUT y cantidad de pasajeros
    @Override
    public List<HabitacionEntity> habitacionesDisponibles(LocalDate checkIn, LocalDate checkOut, Integer pax) {
        // 1. Traemos todas las reservas
        List<ReservaEntity> todasLasReservas = reservaRepository.findAll();

        // 2. Definimos cuáles estados liberan la habitación
        List<Long> idsOcupadas = todasLasReservas.stream()
                // Filtramos solo las que tienen activa = true y NO están en los estados finales
                .filter(r -> Boolean.TRUE.equals(r.getActiva()))
                // Esto es redundante si el estado ya es correcto, pero es una capa extra de seguridad:
                .filter(r -> r.getEstadoReserva() != EstadoReserva.CANCELADA &&
                        r.getEstadoReserva() != EstadoReserva.AUSENTE &&
                        r.getEstadoReserva() != EstadoReserva.INTERRUMPIDA &&
                        r.getEstadoReserva() != EstadoReserva.CONCLUIDA)
                // Filtro de fechas solapadas
                .filter(r -> checkIn.isBefore(r.getCheckOut()) && checkOut.isAfter(r.getCheckIn()))
                .map(r -> r.getHabitacionEntity().getId())
                .distinct()
                .toList();
        // 3. Retornamos las habitaciones que cumplen capacidad y NO están en la lista de ocupadas
        return habitacionService.findAll().stream()
                .filter(h -> h.getCapacidad() >= pax)
                .filter(h -> !idsOcupadas.contains(h.getId()))
                .toList();
    }

    //6.1.2. Listar DTOResponse de habitaciones disponibles segun fechas de CHECK-IN, CHECK-OUT y cantidad de pasajeros
    @Override
    public List<HabitacionDTOResponse>mostrarHabitacionesDisponibles(LocalDate checkIn,LocalDate checkOut,Integer pax){
        List<HabitacionEntity>habitaciones = habitacionesDisponibles(checkIn,checkOut,pax);
        return habitaciones.stream()
                .map(habitacionMapper::toResponse)
                .toList();
    }

    //6.2. Listar reservas con CHECK-IN para el dia de hoy
    @Override
    public List<ReservaDTOResponse>checkIndelDia(){
        return reservaRepository.findByActiva(true).stream()
                .filter(r->r.getCheckIn().equals(LocalDate.now()))
                .map(reservaMapper::toDto)
                .toList();
    }

    //6.3. Listar reservar ACTIVAS con estado PENDIENTE a X dias del CHECK-IN
    @Override
    public List<ReservaDTOResponse>reservasParaConfirmarAxDiasDelCheckIn(Integer x){
        return reservaRepository.findByActiva(true).stream()
                .filter(r->r.getEstadoReserva().equals(EstadoReserva.PENDIENTE))
                .filter(r -> LocalDate.now().plusDays(x).isEqual(r.getCheckIn()))
                .map(reservaMapper::toDto)
                .toList();
    }

    //7. Procesamiento
    //7.1. Determinar ausencia de reserva. Actualzia el estado de la reserva a AUSENTE y la pasa a reserva no activa
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
