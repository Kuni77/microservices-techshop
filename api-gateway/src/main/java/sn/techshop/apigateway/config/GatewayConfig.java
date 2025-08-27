package sn.techshop.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route avec des filtres personnalisés et retry
                .route("user-service-advanced", r -> r
                        .path("/api/auth/**", "/api/users/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Gateway", "techshop-gateway")
                                .addResponseHeader("X-Response-Time", String.valueOf(System.currentTimeMillis()))
                                .retry(config -> config.setRetries(3).setSeries(HttpStatus.Series.SERVER_ERROR))
                        )
                        .uri("lb://user-service")
                )
                .build();
    }
}