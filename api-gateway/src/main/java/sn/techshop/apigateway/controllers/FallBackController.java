package sn.techshop.apigateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {
    @RequestMapping("/fallback/user-service")
    public Mono<String> userServiceFallback() {
        return Mono.just("{\"error\":\"User Service is temporarily unavailable. Please try again later.\",\"status\":503}");
    }

    @RequestMapping("/fallback/product-service")
    public Mono<String> productServiceFallback() {
        return Mono.just("{\"error\":\"Product Service is temporarily unavailable. Please try again later.\",\"status\":503}");
    }
}
