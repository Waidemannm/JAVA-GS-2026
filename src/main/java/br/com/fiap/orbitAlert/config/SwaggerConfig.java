package br.com.fiap.orbitAlert.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    final String AUTORIZACAO = "bearerAuth";

    @Bean
    OpenAPI configurarSwagger() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(AUTORIZACAO))
                .components(new Components().addSecuritySchemes(AUTORIZACAO,
                        new SecurityScheme()
                                .name(AUTORIZACAO)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("OrbitAlert API")
                        .description("Plataforma de alertas precoces de desastres naturais que combina " +
                                "dados do satélite Sentinel-1 (ESA/Copernicus) com inteligência artificial " +
                                "generativa para antecipar eventos de deslizamento e enchente com até 48 horas " +
                                "de antecedência. API Java Spring Boot — hub central da solução.")
                        .summary("OrbitAlert — Economia Espacial · FIAP Global Solution 2026/1")
                        .version("1.0.0"));
    }
}