    package ar.edu.utn.frmdp.ultimate_hotel_software.repository;

    import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoEstadia;
    import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    @Repository
    public interface EstadiaRepository extends JpaRepository<EstadiaEntity,Long> {

        List<EstadiaEntity>findByActiva(Boolean activa);
        List<EstadiaEntity>findByEstado(EstadoEstadia estadoEstadia);
        boolean existsByReservaEntity_HabitacionEntity_Id(Long habitacionId);
    }
