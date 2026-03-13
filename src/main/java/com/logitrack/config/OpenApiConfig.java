package com.logitrack.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "LogiTrack Pro API",
                version = "v1",
                description = "API para gestao de frota, manutencoes e indicadores."
        )
)
public class OpenApiConfig {
}
