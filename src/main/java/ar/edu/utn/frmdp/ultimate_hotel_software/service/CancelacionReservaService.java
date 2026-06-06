package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.CancelacionReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.CancelacionReservaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.CancelacionReservaDTOResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CancelacionReservaService {

    CancelacionReservaEntity crear(CancelacionReservaEntity cancelacion);

    List<CancelacionReservaDTOResponse> historialCancelaciones();

    List<CancelacionReservaDTOResponse> findPorApellido(String apellido);

    void depurarHistorialCancelaciones();

    CancelacionReservaDTOResponse findById(Long id);

    CancelacionReservaEntity findEntityById(Long id);
}
