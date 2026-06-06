package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoEstadia;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EstadiaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EstadiaDTOResponse;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EstadiaService {

    Acá tenés la interfaz formal EstadiaService basada exactamente en tu archivo de implementación actual. Armé el contrato respetando todos tus métodos de negocio, los buscadores por apellido/DNI, el historial por habitación y los KPIs que estuviste puliendo

    List<EstadiaDTOResponse> listar(Boolean activo);

    @Transactional
    EstadiaDTOResponse checkIn(EstadiaDTORequest estadiaDTORequest);

    //Buscar estadia por ID
    EstadiaEntity getEntityById(Long id);

    EstadiaDTOResponse interrumpirEstadia(Long id, String motivo);

    EstadiaDTOResponse pagarEstadia(Long id);

    EstadiaDTOResponse checkOutEstadia(Long id);

    void validacionesCheckOut(EstadiaEntity estadia);

    List<EstadiaDTOResponse>findByEstado(EstadoEstadia estadoEstadia);

    List<EstadiaDTOResponse>estadiaPorApellido(String apellido);

    List<EstadiaDTOResponse>estadiaPorDni(String dni);

    List<EstadiaDTOResponse>HistorialEstadiasPorHabitacion(Integer numeroHabitacion);

    EstadiaDTOResponse findById(Long id);

    //kpis
    Double porcentajeOcupacionPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

    Integer cantidadEstadiasEnCurso();

    /*
        solo abonan en el checkin
        si quieren exteneder la estadia pueden solo si su habitacion no fue reservada
        la metodologia es finalizar la estadia (pagada en el checkin)
        crear otra reserva y generar la estadia nueva(abonando en el nuevo checkin)
        * */
    Double recaudacionCheckInsDelDia();

    Map<String,Double>recaudacionEstadiasPorMesAnio(Integer year);.

    Java
package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoEstadia;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.EstadiaEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EstadiaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EstadiaDTOResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

    public interface EstadiaService {

        List<EstadiaDTOResponse> listar(Boolean activo);

        EstadiaDTOResponse checkIn(EstadiaDTORequest estadiaDTORequest);

        EstadiaEntity getEntityById(Long id);

        EstadiaDTOResponse interrumpirEstadia(Long id, String motivo);

        EstadiaDTOResponse pagarEstadia(Long id);

        EstadiaDTOResponse checkOutEstadia(Long id);

        List<EstadiaDTOResponse> checkOutdelDia();

        List<EstadiaDTOResponse> findByEstado(EstadoEstadia estadoEstadia);

        List<EstadiaDTOResponse> estadiaPorApellido(String apellido);

        List<EstadiaDTOResponse> estadiaPorDni(String dni);

        List<EstadiaDTOResponse> HistorialEstadiasPorHabitacion(Integer numeroHabitacion);

        EstadiaDTOResponse findById(Long id);

        // KPIs y Métricas de Gestión
        Double porcentajeOcupacionPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

        Integer cantidadEstadiasEnCurso();

        Double recaudacionCheckInsDelDia();

        Map<String, Double> recaudacionEstadiasPorMesAnio(Integer year);
}
