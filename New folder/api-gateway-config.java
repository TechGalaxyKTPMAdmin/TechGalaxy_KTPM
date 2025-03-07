package com.techgalaxy.apigateway.config;

import com.techgalaxy.apigateway.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    
    private final AuthorizationFilter authorizationFilter;
    
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth Service
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .uri("lb://AUTH-SERVICE"))
                
                // Product Service
                .route("product-service", r -> r
                        .path("/api/products/**", 
                              "/api/categories/**", 
                              "/api/trademarks/**")
                        .filters(f -> f.filter(authorizationFilter))
                        .uri("lb://PRODUCT-SERVICE"))
                
                // Order Service
                .route("order-service", r -> r
                        .path("/api/orders/**", 
                              "/api/carts/**")
                        .filters(f -> f.filter(authorizationFilter))
                        .uri("lb://ORDER-SERVICE"))
                
                // Payment Service
                .route("payment-service", r -> r
                        .path("/api/payments/**")
                        .filters(f -> f.filter(authorizationFilter))
                        .uri("