package ar.edu.utn.frmdp.ultimate_hotel_software.service;


import ar.edu.utn.frmdp.ultimate_hotel_software.models.CancelacionReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.ReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.CancelacionReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.CancelacionReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.HabitacionDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ReservaDTOResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReservaService {

    List<ReservaDTOResponse> listar(Boolean activa);

    ReservaDTOResponse crearReserva(ReservaDTORequest request);

    ReservaEntity findEntityById(Long id);

    ReservaDTOResponse findById(Long id);

    List<HabitacionEntity> habitacionesDisponibles(LocalDate checkIn, LocalDate checkOut, Integer pax);

    void update(ReservaEntity reserva);

    ReservaDTOResponse confirmarReserva(Long id);

    CancelacionReservaDTOResponse cancelarReserva(CancelacionReservaDTORequest request);

    void procesarAusenciaDeReservas();

    List<ReservaDTOResponse> checkIndelDia();

    List<ReservaDTOResponse> reservasParaConfirmarAxDiasDelCheckIn(Integer x);

    List<HabitacionDTOResponse>mostrarHabitacionesDisponibles(LocalDate checkIn, LocalDate checkOut, Integer pax);

    }
