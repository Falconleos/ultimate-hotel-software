package ar.edu.utn.frmdp.ultimate_hotel_software.repository;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoHabitacion;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.HabitacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<HabitacionEntity,Long> {

    List<HabitacionEntity> findByEstado(EstadoHabitacion estado);

    Optional<HabitacionEntity> findByNumero(Integer numero);
}
