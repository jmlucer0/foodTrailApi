package com.foodtrail.foodtrail_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {


    public OpenApiCustomizer openApiCustomiser() {
        return openApi -> openApi
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("FoodTrail-Api")
                        .version("1.0")
                        .description("API REST para la gestión gastronomica, diseñada para organizar pedidos, envios de los mismos y clientes.")
                );

    }

}
