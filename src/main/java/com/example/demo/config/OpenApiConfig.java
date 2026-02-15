package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Alphalearn API",
        version = "v1",
        description = "API for lessons, concepts, contributors, and enrollments.",
        contact = @Contact(
            name = "Alphalearn Team",
            email = "team@alphalearn.com"
        )
    )
)
public class OpenApiConfig {}
