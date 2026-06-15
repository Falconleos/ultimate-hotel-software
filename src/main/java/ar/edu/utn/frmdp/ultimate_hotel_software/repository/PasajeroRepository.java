package ar.edu.utn.frmdp.ultimate_hotel_software.repository;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.PasajeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasajeroRepository extends JpaRepository <PasajeroEntity, Long> {

    Optional<PasajeroEntity> findByDatosPersonaDni(String dni); //Buscar pasajero por DNI
    boolean existsByDatosPersonaDni(String dni);
    boolean existsByDatosPersonaEmail(String email);
    boolean existsByDatosPersonaTelefono(String telefono);
}
