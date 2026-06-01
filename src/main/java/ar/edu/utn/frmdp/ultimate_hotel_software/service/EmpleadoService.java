package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;

import java.util.List;

public interface EmpleadoService {

    EmpleadoEntity findEntityById(Long id);
    List<EmpleadoDTOResponse> getAll();
    EmpleadoDTOResponse createEmpleado(EmpleadoDTORequest empleadoDTORequest);
    void deleteEmpleado (Long id);
    EmpleadoDTOResponse updateEmpleado(Long id, EmpleadoDTORequest empleadoDTORequest);

}
