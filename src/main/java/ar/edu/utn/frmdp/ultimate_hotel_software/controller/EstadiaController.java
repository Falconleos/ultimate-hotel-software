package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.EstadoEstadia;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EstadiaDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EstadiaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EstadiaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estadias")
@RequiredArgsConstructor
@Tag(
        name = "Estadias",
        description = "Operaciones relacionadas con la gestión de estadias"
)
public class EstadiaController {

    private final EstadiaService estadiaService;

    @GetMapping
    public ResponseEntity<List<EstadiaDTOResponse>> listar(
            @RequestParam(value = "activo", required = false) Boolean activo) {
        List<EstadiaDTOResponse> lista = estadiaService.listar(activo);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping("/check-in")
    public ResponseEntity<EstadiaDTOResponse> checkIn(@RequestBody @Valid EstadiaDTORequest request) {
        EstadiaDTOResponse nuevaEstadia = estadiaService.checkIn(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEstadia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadiaDTOResponse> findById(@PathVariable Long id) {
        EstadiaDTOResponse estadia = estadiaService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(estadia);
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping("/{id}/interrumpir")
    public ResponseEntity<EstadiaDTOResponse> interrumpirEstadia(
            @PathVariable Long id,
            @RequestBody Map<String,String> request) {
        String motivo = request.get("motivo");
        EstadiaDTOResponse estadiaInterrumpida = estadiaService.interrumpirEstadia(id, motivo);
        return ResponseEntity.status(HttpStatus.OK).body(estadiaInterrumpida);
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PatchMapping("/{id}/pagar")
    public ResponseEntity<EstadiaDTOResponse> pagarEstadia(@PathVariable Long id) {
        EstadiaDTOResponse estadiaPagada = estadiaService.pagarEstadia(id);
        return ResponseEntity.status(HttpStatus.OK).body(estadiaPagada);
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @PostMapping("/{id}/check-out")
    public ResponseEntity<EstadiaDTOResponse> checkOutEstadia(@PathVariable Long id) {
        EstadiaDTOResponse estadiaConcluida = estadiaService.checkOutEstadia(id);
        return ResponseEntity.status(HttpStatus.OK).body(estadiaConcluida);
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRATIVO') or hasRole('FRANQUERO')")
    @GetMapping("/check-outs-hoy")
    public ResponseEntity<List<EstadiaDTOResponse>> checkOutdelDia() {
        List<EstadiaDTOResponse> lista = estadiaService.checkOutdelDia();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EstadiaDTOResponse>> findByEstado(@PathVariable EstadoEstadia estado) {
        List<EstadiaDTOResponse> lista = estadiaService.findByEstado(estado);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/buscar/apellido")
    public ResponseEntity<List<EstadiaDTOResponse>> estadiaPorApellido(@RequestParam String apellido) {
        List<EstadiaDTOResponse> lista = estadiaService.estadiaPorApellido(apellido);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/buscar/dni")
    public ResponseEntity<List<EstadiaDTOResponse>> estadiaPorDni(@RequestParam String dni) {
        List<EstadiaDTOResponse> lista = estadiaService.estadiaPorDni(dni);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/habitacion/{numero}/historial")
    public ResponseEntity<List<EstadiaDTOResponse>> historialEstadiasPorHabitacion(
            @PathVariable Integer numero) {
        List<EstadiaDTOResponse> historial = estadiaService.HistorialEstadiasPorHabitacion(numero);
        return ResponseEntity.status(HttpStatus.OK).body(historial);
    }

    // --- Endpoints para KPIs ---

    @GetMapping("/kpi/ocupacion")
    public ResponseEntity<Double> porcentajeOcupacionPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        Double porcentaje = estadiaService.porcentajeOcupacionPorRangoFechas(inicio, fin);
        return ResponseEntity.status(HttpStatus.OK).body(porcentaje);
    }

    @GetMapping("/kpi/en-curso/cantidad")
    public ResponseEntity<Integer> cantidadEstadiasEnCurso() {
        Integer cantidad = estadiaService.cantidadEstadiasEnCurso();
        return ResponseEntity.status(HttpStatus.OK).body(cantidad);
    }

    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @GetMapping("/kpi/recaudacion/hoy")
    public ResponseEntity<Double> recaudacionCheckInsDelDia() {
        Double recaudacion = estadiaService.recaudacionCheckInsDelDia();
        return ResponseEntity.status(HttpStatus.OK).body(recaudacion);
    }

    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @GetMapping("/kpi/recaudacion/mensual")
    public ResponseEntity<Map<String, Double>> recaudacionEstadiasPorMesAnio(
            @RequestParam Integer year) {
        Map<String, Double> recaudacionMensual = estadiaService.recaudacionEstadiasPorMesAnio(year);
        return ResponseEntity.status(HttpStatus.OK).body(recaudacionMensual);
    }

}
