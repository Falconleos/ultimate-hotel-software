package ar.edu.utn.frmdp.ultimate_hotel_software.repository;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoReserva;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity,Long> {
    List<ReservaEntity> findByActiva(Boolean activa);
    // En ReservaRepository.java
    List<ReservaEntity> findByEstadoReservaNotIn(List<EstadoReserva> estados);
}
