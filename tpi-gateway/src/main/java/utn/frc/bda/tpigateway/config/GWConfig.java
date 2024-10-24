package utn.frc.bda.tpigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${microservicio-pruebas.url}") String uriPruebas,
                                        @Value("${microservicio-posicion.url}") String uriPosicion,
                                        @Value("${microservicio-reportes.url}") String uriReportes) {
        return builder.routes()
                // Ruteo al Microservicio de Personas
                .route(p -> p.path("/api/pruebas/**").uri(uriPruebas))
                // Ruteo al Microservicio de Entradas
                .route(p -> p.path("/api/posicion/**").uri(uriPosicion))
                // Ruteo al Microservicio de Reportes
                .route(p -> p.path("/api/reportes/**").uri(uriReportes))
                .build();

    }

}
