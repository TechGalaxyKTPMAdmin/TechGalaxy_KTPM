package iuh.fit.se.apigateway.filter;

import iuh.fit.se.apigateway.dto.response.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final RouteValidator routeValidator;
    private final WebClient.Builder webClientBuilder;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Value("lb://UserService")
    private String authServiceUrl;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String requestId = java.util.UUID.randomUUID().toString();
            ServerHttpRequest request = exchange.getRequest();
            log.info("Processing request [{}]: {}", requestId, request.getURI());

            if (routeValidator.isOpenEndpoint(request)) {
                log.info("Open endpoint detected [{}], bypassing authentication", requestId);
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.warn("No Authorization header [{}]", requestId);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            log.info("Authorization header [{}]: {}", requestId, authHeader);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Invalid Authorization header [{}]", requestId);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);
            return validateToken(token, requestId)
                    .flatMap(response -> {
                        log.info("Response [{}]: {}", requestId, response);
                        if (!response.isValid()) {
                            log.warn("Token invalid [{}]", requestId);
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }

                        if (!routeValidator.isPermitAllEndpoint(request)) {
                            String path = request.getPath().value();
                            log.info("Checking permission [{}]: {}", requestId, path);
                            String method = request.getMethod().name();
                            boolean isAllow = response.getPermissions().stream()
                                    .anyMatch(item -> pathMatcher.match(item.getApiPath(), path)
                                            && item.getMethod().equalsIgnoreCase(method));

                            if (!isAllow) {
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                return exchange.getResponse().setComplete();
                            }
                        }

                        ServerHttpRequest modifiedRequest = request.mutate()
                                .header("X-User-ID", response.getUserId())
                                .header("X-User-Email", response.getEmail())
                                .header(HttpHeaders.AUTHORIZATION, authHeader)
                                .build();

                        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                    })
                    .onErrorResume(e -> {
                        log.error("Error validating token", e);
                        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        return exchange.getResponse().setComplete();
                    });
        };


    }

    private Mono<ValidateTokenResponse> validateToken(String token, String requestId) {
        log.info("Sending token to UserService [{}]: {}", requestId, token);
        return webClientBuilder.build()
                .post()
                .uri(authServiceUrl + "/api/accounts/auth/validate-token")
                .header("Authorization", "Bearer " + token)
                .header("X-Request-ID", requestId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DataResponse<ValidateTokenResponse>>() {})
                .map(dataResponse -> {
                    System.out.println(dataResponse.getData());
                    if (dataResponse.getData() != null && !dataResponse.getData().isEmpty()) {
                        return dataResponse.getData().stream().findFirst().orElse(new ValidateTokenResponse(false, null, null, null));
                    } else {
                        return new ValidateTokenResponse(false, null, null, null);
                    }
                })
                .doOnNext(response -> log.info("ValidateTokenResponse [{}]: {}", requestId, response))
                .doOnError(error -> log.error("Error validating token [{}]: {}", requestId, error.getMessage()));
    }


}