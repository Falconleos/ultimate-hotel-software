package ar.edu.utn.frmdp.ultimate_hotel_software.repository;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    List<Reserva> findByActiva(Boolean activa);
}
