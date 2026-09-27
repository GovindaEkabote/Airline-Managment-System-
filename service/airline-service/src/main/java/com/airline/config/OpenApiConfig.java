package com.airline.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Airline Management System - Location Service")
                                .version("1.0")
                                .description("APIs for managing Airlines")
                                .license(
                                        new License()
                                                .name("License")
                                )
                );
    }
}