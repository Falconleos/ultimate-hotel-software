package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EstadiaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EstadiaDTOResponse;

import java.util.List;

public interface EstadiaService {

    List<EstadiaDTOResponse>listar(Boolean activo);
    EstadiaDTOResponse checkIn(EstadiaDTORequest estadiaDTORequest);
    EstadiaDTOResponse checkOutEstadia(Long id);
    EstadiaDTOResponse pagarEstadia(Long id);
    EstadiaDTOResponse interrumpirEstadia(Long id,String motivo);
}
