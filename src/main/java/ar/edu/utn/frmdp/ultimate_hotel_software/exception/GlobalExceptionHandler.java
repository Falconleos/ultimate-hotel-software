package ar.edu.utn.frmdp.ultimate_hotel_software.exception;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ErrorDTOResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTOResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest){

        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( new ErrorDTOResponse(errores, webRequest.getDescription(false)) );
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidIdException(InvalidIdException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(ConflictoDeEstadoException.class)
    public ResponseEntity<ErrorDTOResponse>handleConflictoDeEstadoException(ConflictoDeEstadoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorDTOResponse>handleEmpleadoNoEncontradoException(EmpleadoNoEncontradoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(EstadiaInvalidaException.class)
    public ResponseEntity<ErrorDTOResponse>handleEstadiaInvalidaException(EstadiaInvalidaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }


    @ExceptionHandler(EstadiaNoEncontradaException.class)
    public ResponseEntity<ErrorDTOResponse>handleEstadiaNoEncontradaException(EstadiaNoEncontradaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(FechaInvalidaException.class)
    public ResponseEntity<ErrorDTOResponse>handleFechaInvalidaException(FechaInvalidaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(HabitacionNoDisponibleException.class)
<<<<<<< HEAD
    public ResponseEntity<ErrorDTOResponse>handleHabitacionNoDisponibleException(HabitacionNoDisponibleException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );
=======
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(HabitacionNoDisponibleException ex,WebRequest webRequest){
        log.warn("la habitacion ya esta reservada para esa fecha");
        ErrorDTOResponse error = new ErrorDTOResponse("la habitacion ya esta reservada para esa fecha", webRequest.getDescription(false));
        log.warn("error {}, {}", error.getMensaje(),error.getDescripcion());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( error);
>>>>>>> feature-correcciones-excepciones

    }

    @ExceptionHandler(PasajeroNoEncontradoException.class)
    public ResponseEntity<ErrorDTOResponse>handlePasajeroNoEncontradoException(PasajeroNoEncontradoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(RoleDuplicadoException.class)
    public ResponseEntity<ErrorDTOResponse>handleRoleDuplicadoException(RoleDuplicadoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }
}