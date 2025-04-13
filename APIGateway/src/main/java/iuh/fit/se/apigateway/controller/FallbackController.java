package iuh.fit.se.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> userServiceFallback() {
        return Mono
                .just("{\"message\":\"User Service is temporarily unavailable. Please try again later.\",\"status\":\""
                        + HttpStatus.SERVICE_UNAVAILABLE.value() + "\"}");
    }

    @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> productServiceFallback() {
        return Mono.just(
                "{\"message\":\"Product Service is temporarily unavailable. Please try again later.\",\"status\":\""
                        + HttpStatus.SERVICE_UNAVAILABLE.value() + "\"}");
    }

    @GetMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> orderServiceFallback() {
        return Mono
                .just("{\"message\":\"Order Service is temporarily unavailable. Please try again later.\",\"status\":\""
                        + HttpStatus.SERVICE_UNAVAILABLE.value() + "\"}");
    }

    @GetMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> paymentServiceFallback() {
        return Mono.just(
                "{\"message\":\"Payment Service is temporarily unavailable. Please try again later.\",\"status\":\""
                        + HttpStatus.SERVICE_UNAVAILABLE.value() + "\"}");
    }

    @GetMapping(value = "/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> inventoryServiceFallback() {
        return Mono.just(
                "{\"message\":\"Inventory Service is temporarily unavailable. Please try again later.\",\"status\":\""
                        + HttpStatus.SERVICE_UNAVAILABLE.value() + "\"}");
    }

    @GetMapping(value = "/notification", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> notificationServiceFallback() {
        return Mono.just(
                "{\"message\":\"Notification Service is temporarily unavailable. Please try again later.\",\"status\":\""
                        + HttpStatus.SERVICE_UNAVAILABLE.value() + "\"}");
    }

    @GetMapping(value = "/recommendation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> recommendationServiceFallback() {
        return Mono.just(
                "{\"message\":\"Recommendation Service is temporarily unavailable. Please try again later.\",\"status\":\""
                        + HttpStatus.SERVICE_UNAVAILABLE.value() + "\"}");
    }
}