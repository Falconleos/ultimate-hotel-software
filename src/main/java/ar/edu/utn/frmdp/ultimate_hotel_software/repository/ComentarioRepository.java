package ar.edu.utn.frmdp.ultimate_hotel_software.repository;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long> {

    // 1. Comentarios por estadía
    @Query("SELECT c FROM ComentarioEntity c WHERE c.estadia.id = :estadiaId")
    List<ComentarioEntity> findByEstadiaId(@Param("estadiaId") Long estadiaId);

    // 2. Comentarios por pasajero de la estadía
    // (Asumiendo que en EstadiaEntity tenés una relación 'pasajero' o 'pasajeroEntity')
    @Query("SELECT c FROM ComentarioEntity c WHERE c.estadia.pasajeroEntity.id = :pasajeroId")
    List<ComentarioEntity> findByEstadiaPasajeroEntityId(@Param("pasajeroId") Long pasajeroId);

    // 3. Comentarios por habitación de la estadía
    // (Asumiendo que en EstadiaEntity tenés una relación 'reservaHabitacion' o 'habitacion')
    @Query("SELECT c FROM ComentarioEntity c WHERE c.estadia.reservaEntity.id = :habitacionId")
    List<ComentarioEntity> findByEstadiaReservaHabitacionEntityId(@Param("habitacionId") Long habitacionId);
}