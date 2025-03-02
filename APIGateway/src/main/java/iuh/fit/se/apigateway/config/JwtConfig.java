//package iuh.fit.se.apigateway.config;
//
//import com.nimbusds.jose.util.Base64;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//
//@Configuration
//@Slf4j
//public class JwtConfig {
//
//    @Value("${jwt.base64-secret}")
//    private String SECRET_KEY ;
//
//    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
//
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
//                getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
//        return token -> {
//            try {
//                return jwtDecoder.decode(token);
//            } catch (Exception e) {
//                log.error("Error decoding token", e.getMessage());
//                throw e;
//            }
//        };
//    }
//
//    private SecretKey getSecretKey() {
//        byte[] keyBytes = Base64.from(SECRET_KEY).decode();
//        return new SecretKeySpec(keyBytes, 0, keyBytes.length,
//                JWT_ALGORITHM.getName());
//    }
//}
