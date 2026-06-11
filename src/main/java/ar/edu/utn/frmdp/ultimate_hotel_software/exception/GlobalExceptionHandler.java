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
    public ResponseEntity<ErrorDTOResponse> handleValidation(MethodArgumentNotValidException ex, WebRequest webRequest){

        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( new ErrorDTOResponse(errores, webRequest.getDescription(false)) );
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(InvalidIdException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(ConflictoDeEstadoException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(ConflictoDeEstadoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(EmpleadoNoEncontradoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(EstadiaInvalidaException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(EstadiaInvalidaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }


    @ExceptionHandler(EstadiaNoEncontradaException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(EstadiaNoEncontradaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(FechaInvalidaException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(FechaInvalidaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(HabitacionNoDisponibleException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(HabitacionNoDisponibleException ex,WebRequest webRequest){
        log.warn("la habitacion ya esta reservada para esa fecha");
        ErrorDTOResponse error = new ErrorDTOResponse("la habitacion ya esta reservada para esa fecha", webRequest.getDescription(false));
        log.warn("error {}, {}", error.getMensaje(),error.getDescripcion());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( error);

    }

    @ExceptionHandler(PasajeroNoEncontradoException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(PasajeroNoEncontradoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(RoleDuplicadoException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(RoleDuplicadoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }



}