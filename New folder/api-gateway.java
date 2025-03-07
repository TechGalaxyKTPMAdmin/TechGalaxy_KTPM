package com.techgalaxy.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techgalaxy.apigateway.config.RouteValidator;
import com.techgalaxy.apigateway.dto.ErrorResponse;
import com.techgalaxy.apigateway.dto.ValidateTokenResponse;
import com.techgalaxy.apigateway.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationFilter implements GatewayFilter {
    
    private final RouteValidator routeValidator;
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;
    
    @Value("${services.auth.url}")
    private String authServiceUrl;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        // Skip validation for open endpoints
        if (routeValidator.isOpenEndpoint(request)) {
            return chain.filter(exchange);
        }
        
        // Check if Authorization header exists
        if (!request.getHeaders().containsKey("Authorization")) {
            return handleError(exchange, ErrorCode.UNAUTHORIZED);
        }
        
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return handleError(exchange, ErrorCode.INVALID_TOKEN);
        }
        
        String token = authHeader.substring(7);
        
        // Validate token with auth service
        return validateToken(token)
                .flatMap(response -> {
                    if (!response.isValid()) {
                        return handleError(exchange, ErrorCode.INVALID_TOKEN);
                    }
                    
                    // Check permissions if not open endpoint
                    if (!routeValidator.isPermitAllEndpoint(request)) {
                        String path = request.getPath().value();
                        String method = request.getMethod().name();
                        String requiredPermission = path + ":" + method;
                        
                        List<String> permissions = response.getPermissions();
                        if (permissions == null || !permissions.contains(requiredPermission)) {
                            return handleError(exchange, ErrorCode.ACCESS_DENIED);
                        }
                    }
                    
                    // Forward user information to the microservices
                    ServerHttpRequest modifiedRequest = request.mutate()
                            .header("X-User-ID", response.getUserId())
                            .header("X-User-Email", response.getEmail())
                            .build();
                    
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                })
                .onErrorResume(e -> {
                    log.error("Error validating token", e);
                    return handleError(exchange, ErrorCode.INTERNAL_SERVER_ERROR);
                });
    }
    
    private Mono<ValidateTokenResponse> validateToken(String token) {
        return webClientBuilder.build()
                .post()
                .uri(authServiceUrl + "/api/auth/validate-token")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ValidateTokenResponse.class);
    }
    
    private Mono<Void> handleError(ServerWebExchange exchange, ErrorCode errorCode) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getMessage()
        );
        
        try {
            byte[] responseBytes = objectMapper.writeValueAsBytes(errorResponse);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBytes)));
        } catch (Exception e) {
            log.error("Error writing response", e);
            return response.setComplete();
        }
    }
}
