package com.duoc.API_ChileHub;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API ChileHub")
                        .version("1.0")
                        .description("Documentación de la API de ChileHub")
                        .contact(new Contact()
                                .name("Benjamin Pinto")
                                .email("be.pintor@duocuc.cl")
                        )
                );
    }
}
