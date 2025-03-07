package iuh.fit.se.apigateway.filter;

import iuh.fit.se.apigateway.dto.response.PermissionAuthResponse;
import iuh.fit.se.apigateway.exception.AppException;
import iuh.fit.se.apigateway.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
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

        return validateToken(token)
                .flatMap(response -> {
                    if (!response.isValid()) {
                        return Mono.error(new AppException(ErrorCode.JWT_INVALID));
                    }

                    if (!routeValidator.isPermitAllEndpoint(request)) {
                        String path = request.getPath().value();
                        String method = request.getMethod().name();
                        // Kiểm tra xem có permission nào khớp với đường dẫn và method hay không.
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
class RouteValidator {
    public boolean isOpenEndpoint(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        // log.info("Checking open endpoint for path: {}", path);
        return path.startsWith("/api/v1/user/auth")
                || path.startsWith("/swagger")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/fallback");
    }

    public boolean isPermitAllEndpoint(ServerHttpRequest request) {
        // Nếu cần cho phép một số endpoint không cần kiểm tra permission chi tiết, điều
        // chỉnh ở đây.
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
class ValidateTokenResponse {
    private boolean valid;
    private String userId;
    private String email;
    private List<PermissionAuthResponse> permissions;
}
