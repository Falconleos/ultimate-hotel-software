package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoEstadia;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoReserva;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.EstadiaInvalidaException;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.EstadiaNoEncontradaException;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.EstadiaMapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.ReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EstadiaDTORequest;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EstadiaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.EstadiaRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EstadiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadiaServiceImpl implements EstadiaService {

    private final EmpleadoService empleadoService;
    private EstadiaRepository estadiaRepository;
    private EstadiaMapper estadiaMapper;

    private ReservaServiceImpl reservaService;
    private HabitacionServiceImpl habitacionService;
    private PasajeroServiceImpl pasajeroService;

    @Override
    public List<EstadiaDTOResponse> listar(Boolean activo) {
        List<EstadiaEntity>estadias;
        if(activo==null){
            estadias = estadiaRepository.findAll();
        }else{
            estadias = estadiaRepository.findByActiva(activo);
        }
        return estadias.stream()
                .map(estadiaMapper::toDto)
                .toList();
    }

    @Override
    public EstadiaDTOResponse crear(EstadiaDTORequest estadiaDTORequest) {

        ReservaEntity reserva = reservaService
                .findEntityById(estadiaDTORequest.getReservaId());

        HabitacionEntity habitacion = reserva.getHabitacionEntity();

        EmpleadoEntity empleadoCheckIn = empleadoService
                .findEntityById(estadiaDTORequest.getEmpleadoId());

        PasajeroEntity pasajero = pasajeroService
                .findEntityById(estadiaDTORequest.getPasajeroId());

        validaciones(reserva,habitacion,empleadoCheckIn,pasajero);

        EstadiaEntity estadia = estadiaMapper.toEntity(estadiaDTORequest);
            estadia.setReservaEntity(reserva);
            estadia.setEstado(EstadoEstadia.EN_CURSO);
            estadia.setPasajeroEntity(pasajero);
            estadia.setEmpleadoEntity(empleadoCheckIn);
                Long cantidadNoches = ChronoUnit.DAYS.between(
                        reserva.getCheckIn(),
                        reserva.getCheckOut()
                );
                Double total = cantidadNoches * habitacion.getPrecioPorNoche();
            estadia.setTotal(total);
            estadia.setActiva(true);

        EstadiaEntity estadiaGuardada = estadiaRepository.save(estadia);

        habitacion.setEstado(EstadoHabitacion.OCUPADA);
            habitacionService.updateHabitacion(habitacion);

        reserva.setEstadoReserva(EstadoReserva.INGRESADA);
            reservaService.update(reserva);

        return estadiaMapper.toDto(estadiaGuardada);
    }

    public void validaciones(ReservaEntity reserva,
                             HabitacionEntity habitacion,
                             EmpleadoEntity empleado,
                             PasajeroEntity pasajero){

        if(reserva==null){
            throw new EstadiaInvalidaException("no existe la reserva con ese id");
        }
        if(pasajero==null){
            throw new EstadiaInvalidaException("no existe el pasajero con ese id");
        }
        if(empleado==null){
            throw new EstadiaInvalidaException("no existe el empleado con ese id");
        }

        if(!reserva.getActiva()){
            throw new EstadiaInvalidaException("estadia invalida por reserva " + reserva.getEstadoReserva());
        }
        if(reserva.getEstadoReserva().equals(EstadoReserva.INGRESADA)){
            throw new EstadiaInvalidaException("la reserva ya cuenta con estadia");
        }
        if(habitacion.getEstado().equals(EstadoHabitacion.MANTENIMIENTO) ||
           habitacion.getEstado().equals(EstadoHabitacion.OCUPADA)){
            throw new EstadiaInvalidaException("Habitacion no disponible por " + habitacion.getEstado());
        }
        if(!empleado.getActivo()){
            throw new EstadiaInvalidaException("El empleado no está activo");
        }

    }

    //Buscar estadia por ID
    public EstadiaEntity getEntityById (Long id) {
        return estadiaRepository.findById(id)
                .orElseThrow( ()->new EstadiaNoEncontradaException("Estadia no encontrada"));
    }
}
