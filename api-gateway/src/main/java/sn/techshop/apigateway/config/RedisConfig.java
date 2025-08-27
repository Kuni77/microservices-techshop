package sn.techshop.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class RedisConfig {

    /**
     * Rate limiting par adresse IP
     * Utilisé pour les endpoints publics (produits, recherche)
     */
    @Bean
    @Primary
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            String clientIP = exchange.getRequest()
                    .getHeaders()
                    .getFirst("X-Forwarded-For");

            if (clientIP == null || clientIP.isEmpty()) {
                clientIP = Objects.requireNonNull(exchange.getRequest()
                                .getRemoteAddress())
                        .getAddress()
                        .getHostAddress();
            }

            return Mono.just(clientIP);
        };
    }

    /**
     * Rate limiting par utilisateur (basé sur JWT)
     * Utilisé pour les endpoints authentifiés (auth, users)
     */
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            // Essaie d'extraire l'userId du JWT
            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // TODO: Extraire l'userId du JWT quand on aura l'auth
                // Pour l'instant, utilise l'IP comme fallback
                String token = authHeader.substring(7);
                // Ici on pourrait décoder le JWT pour récupérer l'userId
                return Mono.just("user:" + token.hashCode());
            }

            // Fallback sur IP si pas d'authentification
            return ipKeyResolver().resolve(exchange);
        };
    }

    /**
     * Rate limiting par session
     * Alternative utilisant les cookies de session
     */
    @Bean
    public KeyResolver sessionKeyResolver() {
        return exchange -> {
            String sessionId = exchange.getRequest()
                    .getCookies()
                    .getFirst("JSESSIONID")
                    != null ? Objects.requireNonNull(exchange.getRequest()
                            .getCookies()
                            .getFirst("JSESSIONID"))
                    .getValue() : "anonymous";

            return Mono.just("session:" + sessionId);
        };
    }
}
