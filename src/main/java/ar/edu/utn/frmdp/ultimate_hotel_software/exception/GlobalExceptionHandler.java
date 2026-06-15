package ar.edu.utn.frmdp.ultimate_hotel_software.exception;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ErrorDTOResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
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

    //Excepciones de reservas
    @ExceptionHandler(ReservaNoEncontradaException.class)
    public ResponseEntity<ErrorDTOResponse>handleReservaNoEncontradaException(ReservaNoEncontradaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(ConflictoDeEstadoReservaException.class)
    public ResponseEntity<ErrorDTOResponse>handleConflictoDeEstadoReservaException(ConflictoDeEstadoReservaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(EstadiaNoEncontradaException.class)
    public ResponseEntity<ErrorDTOResponse>handleEstadiaNoEncontradaException(EstadiaNoEncontradaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(EstadiaInvalidaException.class)
    public ResponseEntity<ErrorDTOResponse>handleEstadiaInvalidaException(EstadiaInvalidaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(FechaInvalidaException.class)
    public ResponseEntity<ErrorDTOResponse>handleFechaInvalidaException(FechaInvalidaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    //Excepciones de habitaciones
    @ExceptionHandler(HabitacionNoEncontradaException.class)
    public ResponseEntity<ErrorDTOResponse>handleHabitacionNoEncontradaException(HabitacionNoEncontradaException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );
    }

    @ExceptionHandler(HabitacionNoDisponibleException.class)
    public ResponseEntity<ErrorDTOResponse>handleHabitacionNoDisponibleException(HabitacionNoDisponibleException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );
    }

    @ExceptionHandler(HabitacionEnUsoException.class)
    public ResponseEntity<ErrorDTOResponse>handleHabitacionEnUsoException(HabitacionEnUsoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );
    }

    @ExceptionHandler(HabitacionDuplicadaException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(HabitacionDuplicadaException ex, WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(HabitacionYaEnMantenimientoException.class)
    public ResponseEntity<ErrorDTOResponse>handleHabitacionYaEnMantenimientoException(HabitacionYaEnMantenimientoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );
    }



    //Excepciones de personas
    @ExceptionHandler(PasajeroNoEncontradoException.class)
    public ResponseEntity<ErrorDTOResponse>handlePasajeroNoEncontradoException(PasajeroNoEncontradoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(PasajeroDuplicadoException.class)
    public ResponseEntity<ErrorDTOResponse>handlePasajeroDuplicadoException(PasajeroDuplicadoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorDTOResponse>handleEmpleadoNoEncontradoException(EmpleadoNoEncontradoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(EmpleadoDuplicadoExcepcion.class)
    public ResponseEntity<ErrorDTOResponse>handleEmpleadoDuplicadoExcepcion(EmpleadoDuplicadoExcepcion ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(ActualizacionEmpleadoInvalidaExcepcion.class)
    public ResponseEntity<ErrorDTOResponse>handleEmpleadoDuplicadoExcepcion(ActualizacionEmpleadoInvalidaExcepcion ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }


    @ExceptionHandler(RolNoEncontradoException.class)
    public ResponseEntity<ErrorDTOResponse>handleRolNoEncontradoException(RolNoEncontradoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }


    @ExceptionHandler(RoleDuplicadoException.class)
    public ResponseEntity<ErrorDTOResponse>handleRoleDuplicadoException(RoleDuplicadoException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(InvalidNameException ex,WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", "Autenticación fallida",
                        "message", "Usuario o contraseña incorrectos"
                ));
    }

    @ExceptionHandler(ComentarioNoEncontradoException.class)
    public ResponseEntity<ErrorDTOResponse>handleComentarioNoEncontradoException(ComentarioNoEncontradoException ex, WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }

    /// /////////////////////
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTOResponse>handleInvalidId(Exception ex, WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body( new ErrorDTOResponse(ex.getMessage(), webRequest.getDescription(false)) );

    }


}