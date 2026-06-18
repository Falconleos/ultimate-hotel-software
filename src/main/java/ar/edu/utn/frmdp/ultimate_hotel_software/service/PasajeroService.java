package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.PasajeroDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.PasajeroDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;

import java.util.List;

public interface PasajeroService {

    PasajeroDTOResponse getById (Long id);
    PasajeroEntity findEntityById(Long id);
    List<PasajeroDTOResponse> getAll();
    PasajeroDTOResponse createPasajero(PasajeroDTORequest pasajeroDTORequest);
    void deletePasajero (Long id);
    PasajeroDTOResponse updatePasajero(Long id, PasajeroDTORequest pasajeroDTORequest);
    PasajeroDTOResponse pasajeroPorDni(String dni);
}
