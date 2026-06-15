package ar.edu.utn.frmdp.ultimate_hotel_software.config;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.EmpleadoMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Role;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.DatosPersonalesDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.EmpleadoRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.RoleRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.security.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final EmpleadoMapper empleadoMapper;
    private final EmpleadoRepository empleadoRepository;
    private final ApplicationConfig applicationConfig;

    @Override
    public void run(String... args) throws Exception {
        // Recorremos los valores de tu Enum
        for (RoleType roleType : RoleType.values()) {
            // Si no existe, lo creamos
            if (roleRepository.findByName(roleType).isEmpty()) {
                roleRepository.save(Role.builder().name(roleType).build());
            }
        }

       if(empleadoRepository.findByUsuario("admin").isEmpty()){
           String user = "admin";
           String password = "1234";

           DatosPersonalesDTORequest datosPersonalesDTORequest =
                   new DatosPersonalesDTORequest("nombreAdminGenerico","apellidoAdminGenerico","00000000","generico@email.com","0000-000-0000");

           EmpleadoDTORequest empleadoDTORequest = new EmpleadoDTORequest(datosPersonalesDTORequest, Turno.MANANA,RoleType.ADMINISTRATIVO,user,password);
           EmpleadoEntity empleadoEntity = empleadoMapper.toEntity(empleadoDTORequest);
           empleadoEntity.setActivo(true);
           empleadoEntity.setPassword(applicationConfig.passwordEncoder().encode(empleadoEntity.getPassword()));
           empleadoEntity.setFechaIngreso(LocalDate.now());

           // --- NUEVO: ASIGNAR EL ROL ---
           Role adminRole = roleRepository.findByName(RoleType.ADMINISTRATIVO)
                   .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));

           // Suponiendo que EmpleadoEntity tiene una relación @ManyToMany o @ManyToOne con Role
           empleadoEntity.setRoles(Collections.singleton(adminRole));
           // ----------------------------

           empleadoRepository.save(empleadoEntity);
       }

    }




}
