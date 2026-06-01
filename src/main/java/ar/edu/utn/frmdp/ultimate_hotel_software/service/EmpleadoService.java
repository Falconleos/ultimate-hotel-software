package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;

import java.util.List;

public interface EmpleadoService {

    EmpleadoEntity findEntityById(Long id);
    List<EmpleadoReservaDTOResponse> getAll();
    EmpleadoReservaDTOResponse createEmpleado(EmpleadoDTORequest empleadoDTORequest);
    void deleteEmpleado (Long id);
    EmpleadoReservaDTOResponse updateEmpleado(Long id, EmpleadoDTORequest empleadoDTORequest);

}
