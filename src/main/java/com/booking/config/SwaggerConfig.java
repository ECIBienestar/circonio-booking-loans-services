package com.booking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Booking Hall and Iteam Loans API")
                        .version("1.0")
                        .description("API documentation for the Booking application")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Booking Team")
                                .email("")));
    }
}
