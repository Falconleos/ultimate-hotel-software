package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoEstadia;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoReserva;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.*;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.EstadiaMapper;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.ReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.CancelacionReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EstadiaDTORequest;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EstadiaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.EstadiaRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EstadiaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadiaServiceImpl implements EstadiaService {

        private final EmpleadoService empleadoService;
        private final EstadiaRepository estadiaRepository;
        private final EstadiaMapper estadiaMapper;

        private final ReservaServiceImpl reservaService;
        private final HabitacionServiceImpl habitacionService;
        private final PasajeroServiceImpl pasajeroService;

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

        @Transactional
        @Override
        public EstadiaDTOResponse checkIn(EstadiaDTORequest estadiaDTORequest) {

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
                estadia.setTotal(reserva.getTotal());
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
                throw new EstadiaInvalidaException("check in imposible porque la habitacion "+ habitacion.getNumero() +" está "+ habitacion.getEstado());
            }
            if(!empleado.getActivo()){
                throw new EstadiaInvalidaException("El empleado no está activo");
            }

            if(reserva.getCheckIn().isBefore(LocalDate.now())){
                throw new FechaInvalidaException("El ingreso solo se permite el dia del checkin reservado");
            }

            if(reserva.getCheckIn().isAfter(LocalDate.now())){
                throw new FechaInvalidaException("El ingreso solo se permite el dia del checkin reservado");
            }

        }

        //Buscar estadia por ID
        @Override
        public EstadiaEntity getEntityById(Long id) {
            return estadiaRepository.findById(id)
                    .orElseThrow( ()->new EstadiaNoEncontradaException("Estadia no encontrada"));
        }

        @Override
        public EstadiaDTOResponse interrumpirEstadia(Long id, String motivo){

            EstadiaEntity estadia = getEntityById(id);
            ReservaEntity reserva = estadia.getReservaEntity();
            HabitacionEntity habitacion = reserva.getHabitacionEntity();

            validacionesInterrupcion(estadia);

            estadia.setEstado(EstadoEstadia.INTERRUMPIDA);
            estadiaRepository.save(estadia);

                CancelacionReservaDTORequest request = new CancelacionReservaDTORequest();
                    request.setMotivo(motivo);
                    request.setReserva_id(reserva.getId());

                    reserva.setEstadoReserva(EstadoReserva.PENDIENTE);//se setea el estado para poder cancelarse
                    reservaService.cancelarReserva(request);
                        habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
                        habitacionService.updateHabitacion(habitacion);
                    //luego de interrumpir la estadia y de cancelar la reserva se habilita la habitacion nuevamente.

            return estadiaMapper.toDto(estadia);
        }


        public void validacionesInterrupcion(EstadiaEntity estadia){
            if(estadia.getEstado().equals(EstadoEstadia.COMPLETADA)){
                throw new ConflictoDeEstadoReservaException("La estadía ya concluyó");
            }else if (estadia.getPagada() == false){
                throw new ConflictoDeEstadoReservaException("Antes de interrumpir la estadía debe abonarse");
            }else if(estadia.getEstado().equals(EstadoEstadia.INTERRUMPIDA)){
                throw new ConflictoDeEstadoReservaException("La estadía ya fue interrumpida");
            }
        }

        @Override
        public EstadiaDTOResponse pagarEstadia(Long id){

            EstadiaEntity estadia = getEntityById(id);
            LocalDate checkIn = estadia.getReservaEntity().getCheckOut();

            if(!checkIn.equals(LocalDate.now())){
                if(estadia.getPagada()==true){
                    throw new ConflictoDeEstadoReservaException("La estadia ya esta pagada");
                }
            }

            estadia.setPagada(true);
            return estadiaMapper.toDto(estadia);
        }

        @Override
        public EstadiaDTOResponse checkOutEstadia(Long id){

            EstadiaEntity estadia = getEntityById(id);
                ReservaEntity reserva = estadia.getReservaEntity();

            validacionesCheckOut(estadia);

            estadia.setEstado(EstadoEstadia.COMPLETADA);
            estadia.setActiva(false);
                estadiaRepository.save(estadia);
                reserva.setEstadoReserva(EstadoReserva.CONCLUIDA);
                    reservaService.update(reserva);

            return estadiaMapper.toDto(estadia);
        }

        public void validacionesCheckOut(EstadiaEntity estadia){
            if(estadia.getReservaEntity().getCheckOut().isAfter(LocalDate.now()) ){
                throw new ConflictoDeEstadoReservaException("No se puede realizar el checkOut " +
                        "solo se puede interrumpir, por ser antes de tiempo");
            }
            if(estadia.getPagada()==false){
                throw new ConflictoDeEstadoReservaException("Antes del check out debe pagar la estadia");
            }
        }

        public List<EstadiaDTOResponse> checkOutdelDia(){

            return estadiaRepository.findByActiva(true).stream()
                    .map(estadiaMapper::toDto)
                    .toList();
        }

        @Override
        public List<EstadiaDTOResponse>findByEstado(EstadoEstadia estadoEstadia){
            return estadiaRepository.findByEstado(estadoEstadia).stream()
                    .map(estadiaMapper::toDto)
                    .toList();
        }

        @Override
        public List<EstadiaDTOResponse>estadiaPorApellido(String apellido){

            List<EstadiaEntity>estadias=estadiaRepository.findAll();

            return estadias.stream()
                    .filter(e -> e.getPasajeroEntity()
                            .getDatosPersona().getApellido().contains(apellido))
                    .map(estadiaMapper::toDto)
                    .toList();
        }

        @Override
        public List<EstadiaDTOResponse>estadiaPorDni(String dni){

            List<EstadiaEntity>estadias=estadiaRepository.findAll();

            return estadias.stream()
                    .filter(e -> e.getPasajeroEntity()
                            .getDatosPersona().getDni().contains(dni))
                    .map(estadiaMapper::toDto)
                    .toList();
        }

        @Override
        public List<EstadiaDTOResponse>HistorialEstadiasPorHabitacion(Integer numeroHabitacion){

            List<EstadiaEntity>estadias=estadiaRepository.findAll();

            return estadias.stream()
                    .filter(e -> e.getReservaEntity()
                            .getHabitacionEntity().getNumero().equals(numeroHabitacion))
                    .sorted(Comparator.comparing((EstadiaEntity e)->e.getReservaEntity().getCheckOut()).reversed())
                    .map(estadiaMapper::toDto)
                    .toList();
        }

        @Override
        public EstadiaDTOResponse findById(Long id){
            return estadiaMapper.toDto(getEntityById(id));
        }


        //kpis
        @Override
        public Double porcentajeOcupacionPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin){
            Integer cantidadHabitaciones = habitacionService.cantidadHabitaciones();
            if (cantidadHabitaciones == 0) {
                return 0.0;
            }
            long cantidadEstadiasRango = estadiaRepository.findAll().stream()
                    .filter(e->{
                        LocalDate checkIn = e.getReservaEntity().getCheckIn();
                        LocalDate checkOut = e.getReservaEntity().getCheckOut();
                        return (checkIn.equals(fechaInicio))||(checkIn.isAfter(fechaInicio)) &&
                                (checkOut.equals(fechaFin))||(checkOut.isBefore(fechaFin));
                    }).count();

            return (cantidadEstadiasRango * 100.0) / cantidadHabitaciones;
        }

        @Override
        public Integer cantidadEstadiasEnCurso(){
            return estadiaRepository.findByEstado(EstadoEstadia.EN_CURSO).size();
        }

        /*
        solo abonan en el checkin
        si quieren exteneder la estadia pueden solo si su habitacion no fue reservada
        la metodologia es finalizar la estadia (pagada en el checkin)
        crear otra reserva y generar la estadia nueva(abonando en el nuevo checkin)
        * */
        @Override
        public Double recaudacionCheckInsDelDia(){
            return estadiaRepository.findAll().stream()
                    .filter(e ->e.getReservaEntity().getCheckIn().equals(LocalDate.now()))
                    .filter(e->e.getPagada().equals(true))
                    .map(EstadiaEntity::getTotal)
                    .reduce(0.0, Double::sum);
        }

        @Override
        public Map<String,Double>recaudacionEstadiasPorMesAnio(Integer year){

            if (year == null) {
                throw new FechaInvalidaException("El año no puede ser nulo");
            }

            int anioActual = LocalDate.now().getYear();
            int anioBaseHotel = 2024;

            if (year > anioActual) {
                throw new FechaInvalidaException("No se puede consultar la recaudación de un año futuro");
            }

            if (year < anioBaseHotel) {
                throw new IllegalArgumentException("El año consultado es anterior al inicio de operaciones del hotel (" + anioBaseHotel + ")");
            }

            return estadiaRepository.findAll().stream()
                    .filter(e -> e.getReservaEntity().getCheckIn().getYear()==year)
                    .filter(e -> e.getPagada().equals(true))
                    .collect(Collectors.groupingBy(
                            e -> e.getReservaEntity().getCheckIn().getMonth().name(),
                            Collectors.summingDouble(EstadiaEntity::getTotal)
                    ));
        }



}
