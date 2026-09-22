package com.rutaia.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rutaiaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RutaIA - API de recomendación de cursos")
                        .description("""
                                API REST que permite registrar estudiantes, consultar el catálogo de cursos \
                                y obtener recomendaciones académicas a partir de una necesidad escrita en \
                                lenguaje natural, usando búsqueda semántica (Qdrant) y RAG (OpenRouter).

                                El frontend solo debe consumir esta API; nunca debe llamar directamente a \
                                n8n, Qdrant u OpenRouter.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo RutaIA")));
    }
}