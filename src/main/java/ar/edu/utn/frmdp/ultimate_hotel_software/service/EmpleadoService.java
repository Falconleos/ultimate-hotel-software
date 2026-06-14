package ar.edu.utn.frmdp.ultimate_hotel_software.service;


import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;

import java.util.List;

public interface EmpleadoService {

    EmpleadoEntity findEntityById(Long id);
    EmpleadoDTOResponse getById (Long id);
    List<EmpleadoDTOResponse> getAll();
    EmpleadoDTOResponse createEmpleado(EmpleadoDTORequest empleadoDTORequest);
    void deleteEmpleado (Long id);
    EmpleadoDTOResponse updateEmpleado(Long id, EmpleadoDTORequest empleadoDTORequest);
    EmpleadoDTOResponse cambiarTurno(Long id, Turno turno);
    EmpleadoDTOResponse cambiarCargo(Long id, RoleType roleType);
    EmpleadoDTOResponse cambiarEstado (Long id);
    EmpleadoEntity findByUsuario(String usuario);
}
