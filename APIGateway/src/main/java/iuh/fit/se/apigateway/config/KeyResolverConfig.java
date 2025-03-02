//package iuh.fit.se.apigateway.config;
//
//import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//// KeyResolver bean to resolve the key for rate limiting with the user ID
//@Configuration
//public class KeyResolverConfig {
//
//    private final JwtDecoder jwtDecoder;
//
//    public KeyResolverConfig(JwtDecoder jwtDecoder) {
//        this.jwtDecoder = jwtDecoder;
//    }
//
//    @Bean
//    public KeyResolver userKeyResolver() {
//        return exchange -> {
//            String token = extractJwtToken(exchange);
//            if (token != null) {
//                String userId = extractUserIdFromToken(token);
//                if (userId != null) {
//                    return Mono.just(userId);
//                }
//            }
//            return Mono.just(getClientIp(exchange));
//        };
//    }
//
//    /**
//     * Trích xuất JWT từ request header "Authorization"
//     */
//    private String extractJwtToken(ServerWebExchange exchange) {
//        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//        return null;
//    }
//
//    /**
//     * Giải mã JWT bằng Spring Security OAuth2 và lấy `user.id`
//     */
//    private String extractUserIdFromToken(String token) {
//        try {
//            Jwt jwt = jwtDecoder.decode(token);
//            return jwt.getClaimAsMap("user").get("id").toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * Lấy địa chỉ IP của client nếu không có JWT
//     */
//    private String getClientIp(ServerWebExchange exchange) {
//        return exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
//    }
//}
