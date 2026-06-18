package ar.edu.utn.frmdp.ultimate_hotel_software.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI configurarOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ultimate Hotel API")
                        .version("1.0")
                        .description("Documentacion asociada a la aplicación Ultimate Hotel con OpenAPI y Spring Boot. La aplicacion gestiona las operaciones de un hotel mediante una API REST. El sistema administra habitaciones, empleados, reservas y estadias de pasajeros facilitando el control de los procesos operativos y mejorando la gestion de la información dentro del establecimiento "));
    }
}
