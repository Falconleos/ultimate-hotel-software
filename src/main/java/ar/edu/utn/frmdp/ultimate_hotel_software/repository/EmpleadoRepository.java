package ar.edu.utn.frmdp.ultimate_hotel_software.repository;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity,Long> {
    List<EmpleadoEntity> findByTurno (Turno turno);
    List<EmpleadoEntity> findByRoleType(RoleType roleType);
    List<EmpleadoEntity> findByActivoTrue ();
    List<EmpleadoEntity> findByActivoFalse ();
    Optional<EmpleadoEntity> findByUsuario(String usuario);

}
