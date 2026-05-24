package ar.edu.utn.frmdp.ultimate_hotel_software.service;


import ar.edu.utn.frmdp.ultimate_hotel_software.models.Habitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ReservaDTOResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReservaService {

    List<ReservaDTOResponse> listar(Boolean activa);
    ReservaDTOResponse crearReserva(ReservaDTORequest request);
    List<Habitacion>habitacionesDisponibles(LocalDate checkIn, LocalDate checkOut, Integer pax);


}
