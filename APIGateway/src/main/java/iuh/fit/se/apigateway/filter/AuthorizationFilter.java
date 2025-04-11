package iuh.fit.se.apigateway.filter;

import iuh.fit.se.apigateway.dto.response.PermissionAuthResponse;
import iuh.fit.se.apigateway.exception.AppException;
import iuh.fit.se.apigateway.exception.ErrorCode;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationFilter implements GatewayFilter {

    private final RouteValidator routeValidator;
    private final WebClient.Builder webClientBuilder;

    @Value("${services.auth.url:lb://UserService}")
    private String authServiceUrl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("Processing request: {}", request.getURI());

        if (routeValidator.isOpenEndpoint(request)) {
            log.info("Open endpoint detected, bypassing authentication");
            return chain.filter(exchange);
        }

        if (!request.getHeaders().containsKey("Authorization")) {
            return Mono.error(new AppException(ErrorCode.NO_LOGIN));
        }

        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.error(new AppException(ErrorCode.JWT_INVALID));
        }

        String token = authHeader.substring(7);
        System.out.println("Token: " + token);
        return validateToken(token)
                .flatMap(response -> {
                    if (!response.isValid()) {
                        return Mono.error(new AppException(ErrorCode.JWT_INVALID));
                    }

                    if (!routeValidator.isPermitAllEndpoint(request)) {
                        String path = request.getPath().value();
                        String method = request.getMethod().name();
                        boolean isAllow = response.getPermissions().stream()
                                .anyMatch(item -> item.getApiPath().equals(path)
                                        && item.getMethod().equals(method));
                        if (!isAllow) {
                            return Mono.error(new AppException(ErrorCode.NO_PERMISSION));
                        }
                    }

                    ServerHttpRequest modifiedRequest = request.mutate()
                            .header("X-User-ID", response.getUserId())
                            .header("X-User-Email", response.getEmail())
                            .build();

                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                })
                .onErrorResume(e -> {
                    if (e instanceof AppException) {
                        return Mono.error(e);
                    }
                    log.error("Error validating token", e);
                    return Mono.error(new AppException(ErrorCode.UNCATEGORIZED_ERROR));
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
}

/**
 * RouteValidator kiểm tra các endpoint mở (không cần xác thực).
 * Ở đây, tất cả các request có đường dẫn bắt đầu với "/api/v1/user/auth" (như
 * login, register, …)
 * cũng như các endpoint swagger và API docs sẽ được bỏ qua.
 */
@Component
@Slf4j
class RouteValidator {

    private final PathPatternParser pathPatternParser = new PathPatternParser();

    private final List<String> whiteList = List.of(
            "/", "/api/accounts/auth/register", "/api/accounts/auth/login",
            "/storage/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
            "/payment/**", "/file", "/files", "/actuator/**", "/actuator/health", "/health/**", "/inventory/**");

    private final List<String> whiteListGetOnly = List.of(
            "/products/**", "/colors/**", "/trademarks/**", "/memories/**",
            "/usageCategories/**", "/attributes/**", "/product-feedbacks/**", "/colors", "/trademarks");

    public boolean isOpenEndpoint(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        String method = request.getMethod().name();
        log.info("Checking path: {}", path);

        for (String pattern : whiteList) {
            PathPattern pathPattern = pathPatternParser.parse(pattern);
            log.info("Matching pattern: {} against path: {}", pattern, path);
            if (pathPattern.matches(request.getPath().pathWithinApplication())) {
                log.info("Path {} matches pattern {}", path, pattern);
                return true;
            }
        }

        if ("GET".equalsIgnoreCase(method)) {
            for (String pattern : whiteListGetOnly) {
                PathPattern pathPattern = pathPatternParser.parse(pattern);
                log.info("Matching GET-only pattern: {} against path: {}", pattern, path);
                if (pathPattern.matches(request.getPath().pathWithinApplication())) {
                    log.info("GET Path {} matches GET-only pattern {}", path, pattern);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isPermitAllEndpoint(ServerHttpRequest request) {
        // Có thể mở rộng nếu cần phân loại thêm
        return false;
    }
}

/**
 * ValidateTokenResponse là DTO trả về từ Auth Service sau khi validate token.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
class ValidateTokenResponse {
    private boolean valid;
    private String userId;
    private String email;
    private List<PermissionAuthResponse> permissions;
}
