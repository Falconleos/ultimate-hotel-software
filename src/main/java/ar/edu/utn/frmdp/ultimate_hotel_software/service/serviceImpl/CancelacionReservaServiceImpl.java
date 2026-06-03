package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.exception.InvalidIdException;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.CancelacionReservaMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.CancelacionReservaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.CancelacionReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.CancelacionReservaRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.CancelacionReservaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CancelacionReservaServiceImpl implements CancelacionReservaService{

    private final ReservaServiceImpl reservaService;

    private final CancelacionReservaRepository repository;
    private final CancelacionReservaMapper mapper;

    @Override
    public CancelacionReservaEntity crear(CancelacionReservaEntity cancelacion) {
        cancelacion.setFecha(LocalDateTime.now());
        return repository.save(cancelacion);
    }

    @Override
    public List<CancelacionReservaDTOResponse> historialCancelaciones() {
        List<CancelacionReservaEntity>cancelaciones = repository.findAll();
        return cancelaciones.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<CancelacionReservaDTOResponse> findPorApellido(String apellido) {
        List<CancelacionReservaEntity>cancelaciones = repository.findAll();
        return cancelaciones.stream()
                .filter(c -> c.getReservaEntity().getApellido().contains(apellido))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void depurarHistorialCancelaciones() {
        LocalDateTime haceUnMes = LocalDateTime.now().minusMonths(1);

        List<CancelacionReservaEntity> viejas = repository.findAll().stream()
                .filter(c -> c.getFecha().isBefore(haceUnMes))
                .toList();

        repository.deleteAllInBatch(viejas);
    }

    @Override
    public CancelacionReservaDTOResponse findById(Long id) {
        return mapper.toDto(findEntityById(id));
    }

    @Override
    public CancelacionReservaEntity findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow( ()->new InvalidIdException("Id de cancelacion invalido"));
    }
}
