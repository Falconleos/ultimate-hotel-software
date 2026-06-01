package ar.edu.utn.frmdp.ultimate_hotel_software.repository;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<ComentarioEntity,Long> {
    List<ComentarioEntity> findByEstadiaId(Long estadiaId); //Comentarios por estadia
    List<ComentarioEntity> findByEstadiaPasajeroEntityId(Long pasajeroId); //Comentarios por estadia
    List<ComentarioEntity> findByEstadiaReservaHabitacionEntityId(Long habitacionId); //Comentarios por estadia
}
