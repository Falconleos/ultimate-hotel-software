package ar.edu.utn.frmdp.ultimate_hotel_software.config;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Role;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Recorremos los valores de tu Enum
        for (RoleType roleType : RoleType.values()) {
            // Si no existe, lo creamos
            if (roleRepository.findByName(roleType).isEmpty()) {
                roleRepository.save(Role.builder().name(roleType).build());
            }
        }
    }

}
