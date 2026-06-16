package ar.edu.utn.frmdp.ultimate_hotel_software.config;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.*;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.CancelacionReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.ReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Role;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.DatosPersonalesDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.HabitacionDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.PasajeroDTORequest;
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


}
