package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Cargo;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.EmpleadoMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.ComentarioDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.ComentarioDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoReservaDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.DatosPersonalesEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.EmpleadoRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.ComentarioService;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;
    private final ComentarioService comentarioService;

    //1. Busqueda de empleado
    //1.1 Devuelve entidad
    @Override
    public EmpleadoEntity findEntityById(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow( ()->new RuntimeException() );
    }

    //1.2 Devuelve DTOResponse
    public EmpleadoDTOResponse getById (Long id) {
        return empleadoMapper.toDTO(findEntityById(id));
    }

    //2. Listar empleados
    @Override
    public List<EmpleadoDTOResponse> getAll() {
        return empleadoRepository.findAll().stream()
                .map(empleadoMapper::toDTO)
                .toList();
    }

    //3. Crear empleado
    @Override
    public EmpleadoDTOResponse createEmpleado(EmpleadoDTORequest empleadoDTORequest) {

        //Mapeo a entidad
        EmpleadoEntity empleadoEntity = empleadoMapper.toEntity(empleadoDTORequest);

        //Agregado de informacion
        empleadoEntity.setFechaIngreso(LocalDate.now());
        empleadoEntity.setActivo(true);

        //Guardado en base de datos
        return empleadoMapper.toDTO(empleadoRepository.save(empleadoEntity));
    }

    //4. Borrar empleado
    @Override
    public void deleteEmpleado(Long id) {
        EmpleadoEntity empleadoEntity = findEntityById(id);
        empleadoRepository.delete(empleadoEntity);
    }

    //5. Actualizaciones
    // 5.1. Actualizar empleado (completo)
    @Override
    @Transactional
    public EmpleadoDTOResponse updateEmpleado(Long id, EmpleadoDTORequest empleadoDTORequestModificado) {

        //Buscar entidad a modificar
        EmpleadoEntity empleado = findEntityById(id);
        DatosPersonalesEntity datosPersonalesEntity = empleado.getDatosPersona();

        //Modificacion del empleado
        datosPersonalesEntity.setNombre(empleadoDTORequestModificado.getDatosPersonalesDTORequest().getNombre());
        datosPersonalesEntity.setApellido(empleadoDTORequestModificado.getDatosPersonalesDTORequest().getApellido());
        datosPersonalesEntity.setEmail(empleadoDTORequestModificado.getDatosPersonalesDTORequest().getEmail());
        datosPersonalesEntity.setTelefono(empleadoDTORequestModificado.getDatosPersonalesDTORequest().getTelefono());

        empleado.setDatosPersona(datosPersonalesEntity);
        empleado.setTurno(empleadoDTORequestModificado.getTurno());
        empleado.setCargo(empleadoDTORequestModificado.getCargo());
        empleado.setUsuario(empleadoDTORequestModificado.getUsuario());
        empleado.setPassword(empleadoDTORequestModificado.getPassword());

        //Hibernate detecta cambio en el elemento y modifica la base de datos, no es necesario usar save()
        //Devolucion de DTO
        return empleadoMapper.toDTO(empleado);
    }

    //5.2. Cambiar turno
    @Transactional
    public EmpleadoDTOResponse cambiarTurno(Long id, Turno turno) {

        //Buscar entidad a modificar
        EmpleadoEntity empleado = findEntityById(id);

        //Modificaciones
        empleado.setTurno(turno); //Hibernate detecta cambio en el elemento y modifica la base de datos.

        //Guardado en repositorio y devolucion de DTO
        return empleadoMapper.toDTO(empleado);
    }

    //5.3. Cambiar cargo
    @Transactional
    public EmpleadoDTOResponse cambiarCargo(Long id, Cargo cargo){

        //Buscar entidad a modificar
        EmpleadoEntity empleado = findEntityById(id);

        //Modificaciones
        empleado.setCargo(cargo); //Hibernate detecta cambio en el elemento y modifica la base de datos.

        //Guardado en repositorio y devolucion de DTO
        return empleadoMapper.toDTO(empleado);
    }

    //5.4. Cambiar estado
    @Transactional
    public EmpleadoDTOResponse cambiarEstado (Long id) {

        //Buscar entidad a modificar
        EmpleadoEntity empleado = findEntityById(id);

        //Modificaciones
        empleado.setActivo(!empleado.getActivo()); //Hibernate detecta cambio en el elemento y modifica la base de datos.

        //Guardado en repositorio y devolucion de DTO
        return empleadoMapper.toDTO(empleado);
    }

    //6. Metodos de gestion de comentarios de pasajero

//    //6.1. Crear comentario (solo pasajero)
//    public ComentarioDTOResponse crearComentario (Long id, ComentarioDTORequest comentarioDTORequest) {
//        return comentarioService.crearComentario(id, comentarioDTORequest);
//    }

    //6.2.1. Buscar comentarios por habitacion (solo administrador)
    public List<ComentarioDTOResponse> getComentariosHabitacion(Long habitacion_id) {
        return comentarioService.getComentariosHabitacion(habitacion_id);
    }

    //6.2.2. Buscar comentarios por pasajero (solo administrador)
    public List<ComentarioDTOResponse> getComentarioPasajero(Long pasajero_id) {
        return  comentarioService.getComentarioPasajero(pasajero_id);
    }

//    //6.3. Modificar comentario (solo pasajero)
//    public ComentarioDTOResponse updateComentario(Long id, ComentarioDTORequest comentarioDTORequest) {
//        return comentarioService.updateComentario(id, comentarioDTORequest);
//    }

    //6.4. Elimnar (pasajero y administrador)
    public void deleteComentario(Long id) {
        comentarioService.deleteComentario(id);
    }








}
