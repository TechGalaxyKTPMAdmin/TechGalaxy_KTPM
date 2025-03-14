package iuh.fit.se.apigateway.config;

import iuh.fit.se.apigateway.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

        private final AuthorizationFilter authorizationFilter;

        @Bean
        public RouteLocator routeLocator(RouteLocatorBuilder builder) {
                return builder.routes()
                                // User Service with fallback configuration
                                .route("UserService", r -> r
                                                .path("/api/v1/user/**")
                                                .filters(f -> f
                                                                .filter(authorizationFilter)
                                                                .circuitBreaker(config -> config
                                                                                .setName("userServiceCB")
                                                                                .setFallbackUri("forward:/fallback")))
                                                .uri("lb://UserService"))

                                // Product Service with fallback configuration
//                                .route("ProductService", r -> r
//                                                .path("/api/v1/product/**")
//                                                .filters(f -> f
//                                                                .filter(authorizationFilter)
//                                                                .circuitBreaker(config -> config
//                                                                                .setName("productServiceCB")
//                                                                                .setFallbackUri("forward:/fallback")))
//                                                .uri("lb://ProductService"))

                                // Order Service with fallback configuration
                                .route("OrderService", r -> r
                                                .path("orders/**")
                                                .filters(f -> f
                                                                .filter(authorizationFilter)
                                                                .circuitBreaker(config -> config
                                                                                .setName("orderServiceCB")
                                                                                .setFallbackUri("forward:/fallback")))
                                                .uri("lb://OrderService"))

                                // Payment Service with fallback configuration
                                .route("PaymentService", r -> r
                                                .path("/api/v1/payment/**")
                                                .filters(f -> f
                                                                .filter(authorizationFilter)
                                                                .circuitBreaker(config -> config
                                                                                .setName("paymentServiceCB")
                                                                                .setFallbackUri("forward:/fallback")))
                                                .uri("lb://PaymentService"))

                                // Inventory Service with fallback configuration
                                .route("InventoryService", r -> r
                                                .path("/api/v1/inventory/**")
                                                .filters(f -> f
                                                                .filter(authorizationFilter)
                                                                .circuitBreaker(config -> config
                                                                                .setName("inventoryServiceCB")
                                                                                .setFallbackUri("forward:/fallback")))
                                                .uri("lb://InventoryService"))

                                // Notification Service with fallback configuration
                                .route("NotificationService", r -> r
                                                .path("/api/v1/notification/**")
                                                .filters(f -> f
                                                                .filter(authorizationFilter)
                                                                .circuitBreaker(config -> config
                                                                                .setName("notificationServiceCB")
                                                                                .setFallbackUri("forward:/fallback")))
                                                .uri("lb://NotificationService"))

                                .build();
        }
}
