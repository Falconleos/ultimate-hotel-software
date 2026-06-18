package ar.edu.utn.frmdp.ultimate_hotel_software.config;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.*;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.*;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.DatosPersonalesDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.PasajeroDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.*;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final EmpleadoService empleadoService;
    private final HabitacionService habitacionService;
    private final PasajeroService pasajeroService;

    private final EmpleadoRepository empleadoRepository;
    private final HabitacionRepository habitacionRepository;
    private final PasajeroRepository pasajeroRepository;

    private final ReservaRepository reservaRepository;
    private final EstadiaRepository estadiaRepository;
    private final CancelacionReservaRepository cancelacionReservaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Recorremos los valores de tu Enum
        for (RoleType roleType : RoleType.values()) {
            // Si no existe, lo creamos
            if (roleRepository.findByName(roleType).isEmpty()) {
                roleRepository.save(Role.builder().name(roleType).build());
            }
        }

        if(empleadoRepository.findAll().isEmpty() &&
            habitacionRepository.findAll().isEmpty() &&
            pasajeroRepository.findAll().isEmpty()){

            crearRegistroEmpleadoHabitacionPasajero(empleadoService,
                    habitacionService,
                    pasajeroService);

            crearEstadiaInterrumpida( reservaRepository,
                    estadiaRepository,
                    cancelacionReservaRepository,
                    empleadoRepository,
                    habitacionRepository,
                    pasajeroRepository);

            crearEstadiaCheckOutJueves( reservaRepository,
                    estadiaRepository,
                    cancelacionReservaRepository,
                    empleadoRepository,
                    habitacionRepository,
                    pasajeroRepository);

            crearEstadiaHistorica1( reservaRepository,
                    estadiaRepository,
                    cancelacionReservaRepository,
                    empleadoRepository,
                    habitacionRepository,
                    pasajeroRepository);

            crearEstadiaHistorica2( reservaRepository,
                    estadiaRepository,
                    cancelacionReservaRepository,
                    empleadoRepository,
                    habitacionRepository,
                    pasajeroRepository);

            cargarEstadiasAnio2025(reservaRepository,
                    estadiaRepository,empleadoRepository,
                    habitacionRepository,pasajeroRepository);
        }


    }

    public void crearRegistroEmpleadoHabitacionPasajero(EmpleadoService empleadoService,
                                            HabitacionService habitacionService,
                                            PasajeroService pasajeroService){

        // 1. Crear 16 Habitaciones (4 de cada tipo con su capacidad específica)
        TipoHabitacion[] tipos = {TipoHabitacion.SIMPLE, TipoHabitacion.DOBLE, TipoHabitacion.TRIPLE, TipoHabitacion.SUITE};
        int[] capacidades = {1, 2, 3, 4};
        int[] numeros = {101, 102, 103, 104, 201, 202, 203, 204, 301, 302, 303, 304, 401, 402, 403, 404};

        for (int i = 0; i < numeros.length; i++) {
            HabitacionDTORequest hab = new HabitacionDTORequest();
            hab.setNumero(numeros[i]);
            hab.setTipo(tipos[i / 4]);
            hab.setCapacidad(capacidades[i / 4]);
            hab.setPrecioPorNoche(100.0 * (i / 4 + 1)); // Precio escalonado por tipo
            habitacionService.save(hab);
        }

        // 2. Crear 1 empleado por cada RoleType con datos personales completos
        int eIndex = 1;
        for (RoleType role : RoleType.values()) {
            EmpleadoDTORequest emp = new EmpleadoDTORequest();
            emp.setDatosPersonalesDTORequest(crearDatosPersonales("Empleado", eIndex));
            emp.setUsuario("usuario_" + role.name().toLowerCase());
            emp.setPassword("1234");
            emp.setRoleType(role);
            emp.setTurno(Turno.MANANA);
            empleadoService.createEmpleado(emp);
            eIndex++;
        }

        // 3. Crear algunos Pasajeros de ejemplo
        for (int i = 1; i <= 5; i++) {
            PasajeroDTORequest pas = new PasajeroDTORequest();
            pas.setDatosPersonalesDTORequest(crearDatosPersonales("Pasajero", 100 + i));
            pasajeroService.createPasajero(pas);
        }


    }

    // Helper para generar datos únicos y evitar errores de validación
    public DatosPersonalesDTORequest crearDatosPersonales(String prefijo, int index) {
        DatosPersonalesDTORequest datos = new DatosPersonalesDTORequest();
        datos.setNombre(prefijo + index);
        datos.setApellido("Apellido" + index);
        datos.setDni(String.valueOf(20000000 + index)); // DNI único
        datos.setEmail(prefijo.toLowerCase() + index + "@hoteltest.com");
        datos.setTelefono("11" + (50000000 + index));
        return datos;
    }

    public void crearEstadiaInterrumpida(ReservaRepository reservaRepository,
                                         EstadiaRepository estadiaRepository,
                                         CancelacionReservaRepository cancelacionReservaRepository,
                                         EmpleadoRepository empleadoRepository,
                                         HabitacionRepository habitacionRepository,
                                         PasajeroRepository pasajeroRepository) {

        var empleado = empleadoRepository.findAll().stream().findFirst().orElseThrow();
        var habitacion = habitacionRepository.findAll().stream().findFirst().orElseThrow();
        var pasajero = pasajeroRepository.findAll().stream().findFirst().orElseThrow();

        // Fechas fijas para garantizar consistencia
        // Check-in: 1 de enero de 2026
        LocalDate fechaCheckIn = LocalDate.of(2026, 1, 1);
        // Check-out original: 10 de enero de 2026
        LocalDate fechaCheckOutOriginal = LocalDate.of(2026, 1, 10);
        // Interrupción: 5 de enero de 2026 (dentro del rango)
        LocalDateTime fechaInterrupcion = LocalDateTime.of(2026, 1, 5, 10, 30);

        // 1. Crear la Reserva
        ReservaEntity reserva = ReservaEntity.builder()
                .checkIn(fechaCheckIn)
                .checkOut(fechaCheckOutOriginal)
                .cantidadPax(2)
                .estadoReserva(EstadoReserva.CANCELADA)
                .nombre("Juan")
                .apellido("Perez")
                .telefono("115647894")
                .observacion("llega 15hs")
                .activa(false)
                .empleadoEntity(empleado)
                .habitacionEntity(habitacion)
                .total(1000.0)
                .build();
        reserva = reservaRepository.save(reserva);

        // 2. Crear la Estadia
        EstadiaEntity estadia = EstadiaEntity.builder()
                .reservaEntity(reserva)
                .estado(EstadoEstadia.INTERRUMPIDA)
                .pasajeroEntity(pasajero)
                .empleadoEntity(empleado)
                .total(1000.0)
                .pagada(true)
                .activa(false)
                .build();
        estadiaRepository.save(estadia);

        // 3. Crear la Cancelación
        CancelacionReservaEntity cancelacion = CancelacionReservaEntity.builder()
                .reservaEntity(reserva)
                .fecha(fechaInterrupcion)
                .motivo("Interrupción anticipada por emergencia médica")
                .build();
        cancelacionReservaRepository.save(cancelacion);
    }

    public void crearEstadiaCheckOutJueves(ReservaRepository reservaRepository,
                                         EstadiaRepository estadiaRepository,
                                         CancelacionReservaRepository cancelacionReservaRepository,
                                         EmpleadoRepository empleadoRepository,
                                         HabitacionRepository habitacionRepository,
                                         PasajeroRepository pasajeroRepository) {

        var empleado = empleadoRepository.findAll().stream().findFirst().orElseThrow();
        var habitacion = habitacionRepository.findAll().stream().findFirst().orElseThrow();
        var pasajero = pasajeroRepository.findAll().stream().findFirst().orElseThrow();

        // Fechas fijas para garantizar consistencia
        // Check-in: 1 de enero de 2026
        LocalDate fechaCheckIn = LocalDate.of(2026, 6, 16);
        // Check-out original: 10 de enero de 2026
        LocalDate fechaCheckOutOriginal = LocalDate.of(2026, 6, 18);

        // 1. Crear la Reserva
        ReservaEntity reserva = ReservaEntity.builder()
                .checkIn(fechaCheckIn)
                .checkOut(fechaCheckOutOriginal)
                .cantidadPax(2)
                .estadoReserva(EstadoReserva.INGRESADA)
                .nombre("Agus")
                .apellido("Bonnet")
                .telefono("115647894")
                .observacion("Hace las mejores pizza")
                .activa(false)
                .empleadoEntity(empleado)
                .habitacionEntity(habitacion)
                .total(200.0)
                .build();
        reserva = reservaRepository.save(reserva);

        // 2. Crear la Estadia
        EstadiaEntity estadia = EstadiaEntity.builder()
                .reservaEntity(reserva)
                .estado(EstadoEstadia.EN_CURSO)
                .pasajeroEntity(pasajero)
                .empleadoEntity(empleado)
                .total(200.0)
                .pagada(true)
                .activa(false)
                .build();
        estadiaRepository.save(estadia);

    }

    public void crearEstadiaHistorica1(ReservaRepository reservaRepository,
                                         EstadiaRepository estadiaRepository,
                                         CancelacionReservaRepository cancelacionReservaRepository,
                                         EmpleadoRepository empleadoRepository,
                                         HabitacionRepository habitacionRepository,
                                         PasajeroRepository pasajeroRepository) {

        var empleado = empleadoRepository.findAll().stream().toList().get(2);
        var habitacion = habitacionRepository.findAll().stream().findFirst().orElseThrow();
        var pasajero = pasajeroRepository.findAll().stream().findFirst().orElseThrow();

        // Fechas fijas para garantizar consistencia
        // Check-in: 1 de enero de 2026
        LocalDate fechaCheckIn = LocalDate.of(2026, 3, 3);
        // Check-out original: 10 de enero de 2026
        LocalDate fechaCheckOutOriginal = LocalDate.of(2026, 3, 15);

        // 1. Crear la Reserva
        ReservaEntity reserva = ReservaEntity.builder()
                .checkIn(fechaCheckIn)
                .checkOut(fechaCheckOutOriginal)
                .cantidadPax(1)
                .estadoReserva(EstadoReserva.CONCLUIDA)
                .nombre("Carlos")
                .apellido("Castanieda")
                .telefono("2236467978")
                .observacion("llega de noche")
                .activa(false)
                .empleadoEntity(empleado)
                .habitacionEntity(habitacion)
                .total(1200.0)
                .build();
        reserva = reservaRepository.save(reserva);

        // 2. Crear la Estadia
        EstadiaEntity estadia = EstadiaEntity.builder()
                .reservaEntity(reserva)
                .estado(EstadoEstadia.COMPLETADA)
                .pasajeroEntity(pasajero)
                .empleadoEntity(empleado)
                .total(1200.0)
                .pagada(true)
                .activa(false)
                .build();
        estadiaRepository.save(estadia);

    }

    public void crearEstadiaHistorica2(ReservaRepository reservaRepository,
                                       EstadiaRepository estadiaRepository,
                                       CancelacionReservaRepository cancelacionReservaRepository,
                                       EmpleadoRepository empleadoRepository,
                                       HabitacionRepository habitacionRepository,
                                       PasajeroRepository pasajeroRepository) {

        var empleado = empleadoRepository.findAll().stream().toList().get(4);
        var habitacion = habitacionRepository.findAll().stream().findFirst().orElseThrow();
        var pasajero = pasajeroRepository.findAll().stream().findFirst().orElseThrow();

        // Fechas fijas para garantizar consistencia
        // Check-in: 1 de enero de 2026
        LocalDate fechaCheckIn = LocalDate.of(2026, 3, 5);
        // Check-out original: 10 de enero de 2026
        LocalDate fechaCheckOutOriginal = LocalDate.of(2026, 3, 12);

        // 1. Crear la Reserva
        ReservaEntity reserva = ReservaEntity.builder()
                .checkIn(fechaCheckIn)
                .checkOut(fechaCheckOutOriginal)
                .cantidadPax(2)
                .estadoReserva(EstadoReserva.CONCLUIDA)
                .nombre("Jorge")
                .apellido("Borges")
                .telefono("2262422124")
                .observacion("pide libros de madrugada")
                .activa(false)
                .empleadoEntity(empleado)
                .habitacionEntity(habitacion)
                .total(700.0)
                .build();
        reserva = reservaRepository.save(reserva);

        // 2. Crear la Estadia
        EstadiaEntity estadia = EstadiaEntity.builder()
                .reservaEntity(reserva)
                .estado(EstadoEstadia.COMPLETADA)
                .pasajeroEntity(pasajero)
                .empleadoEntity(empleado)
                .total(700.0)
                .pagada(true)
                .activa(false)
                .build();
        estadiaRepository.save(estadia);

    }

    public void cargarEstadiasAnio2025(ReservaRepository reservaRepository,
                                      EstadiaRepository estadiaRepository,
                                      EmpleadoRepository empleadoRepository,
                                      HabitacionRepository habitacionRepository,
                                      PasajeroRepository pasajeroRepository) {

        var empleados = empleadoRepository.findAll();
        var habitaciones = habitacionRepository.findAll();
        var pasajeros = pasajeroRepository.findAll();

        if (empleados.isEmpty() || habitaciones.isEmpty() || pasajeros.isEmpty()) {
            throw new IllegalStateException("Se requieren datos base (empleado, habitacion, pasajero) antes de cargar estadias.");
        }

        int habIndex = 0;
        int pasIndex = 0;

        // Iterar por cada mes del año 2025
        for (int mes = 1; mes <= 12; mes++) {
            // Crear 2 estadias por mes
            for (int i = 1; i <= 2; i++) {
                // Seleccionar habitacion y pasajero de forma cíclica
                HabitacionEntity habitacion = habitaciones.get(habIndex % habitaciones.size());
                PasajeroEntity pasajero = pasajeros.get(pasIndex % pasajeros.size());
                EmpleadoEntity empleado = empleados.get(0); // Usamos el primero como responsable

                // Definir cantidad de pasajeros según capacidad de la habitación
                int cantPax = habitacion.getCapacidad();

                // Definir fechas: dia 5 al 10 de cada mes
                LocalDate checkIn = LocalDate.of(2025, mes, 5);
                LocalDate checkOut = LocalDate.of(2025, mes, 10);

                // 1. Crear Reserva
                ReservaEntity reserva = ReservaEntity.builder()
                        .checkIn(checkIn)
                        .checkOut(checkOut)
                        .cantidadPax(cantPax)
                        .estadoReserva(EstadoReserva.CONCLUIDA)
                        .nombre(pasajero.getDatosPersona().getNombre())
                        .apellido(pasajero.getDatosPersona().getApellido())
                        .telefono(pasajero.getDatosPersona().getTelefono())
                        .activa(false)
                        .empleadoEntity(empleado)
                        .habitacionEntity(habitacion)
                        .total(habitacion.getPrecioPorNoche() * 5)
                        .build();
                reserva = reservaRepository.save(reserva);

                // 2. Crear Estadia
                EstadiaEntity estadia = EstadiaEntity.builder()
                        .reservaEntity(reserva)
                        .estado(EstadoEstadia.COMPLETADA)
                        .pasajeroEntity(pasajero)
                        .empleadoEntity(empleado)
                        .total(reserva.getTotal())
                        .pagada(true)
                        .activa(false)
                        .build();
                estadiaRepository.save(estadia);

                habIndex++;
                pasIndex++;
            }
        }
    }


}
