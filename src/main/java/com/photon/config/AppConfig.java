package com.photon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI().info(getInfo());
    }

    private Info getInfo() {
        return new Info()
                .title("Product Reactive API")
                .version("0.0.1")
                .description("Spring Boot Reactive CRUD API using WebFlux, MapStruct, ProblemDetail, and Swagger");
    }
}
