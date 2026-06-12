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
                        .description("Documentacion asociada a la API REST de Ultimate Hotel con OpenAPI y Spring Boot"));
    }

}
