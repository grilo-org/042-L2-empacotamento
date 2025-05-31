package com.l2.empacotamento.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Empacotamento")
                        .description("API para processamento de empacotamentos com documentação via OpenAPI")
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação da OpenAPI")
                        .url("https://springdoc.org/"));
    }
}
