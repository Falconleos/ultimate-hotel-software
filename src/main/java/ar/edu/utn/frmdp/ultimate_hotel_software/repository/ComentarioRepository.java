package ar.edu.utn.frmdp.ultimate_hotel_software.repository;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long> {

    // 1. Buscar comentarios directamente por el ID de la estadía
    @Query("SELECT c FROM ComentarioEntity c WHERE c.estadia.id = :estadiaId")
    List<ComentarioEntity> findByEstadiaId(@Param("estadiaId") Long estadiaId);

    // 2. Filtrar por Pasajero (Navegación: Comentario -> Estadia -> PasajeroEntity)
    @Query("SELECT c FROM ComentarioEntity c JOIN c.estadia e JOIN e.pasajeroEntity p WHERE p.id = :pasajeroId")
    List<ComentarioEntity> findByEstadiaPasajeroEntityId(@Param("pasajeroId") Long pasajeroId);

    // 3. Buscar comentarios uniendo la estadía para la habitación
    @Query("SELECT c FROM ComentarioEntity c JOIN c.estadia e JOIN e.reservaEntity r JOIN r.habitacionEntity h WHERE h.id = :habitacionId")
    List<ComentarioEntity> findByEstadiaReservaHabitacionEntityId(@Param("habitacionId") Long habitacionId);
}