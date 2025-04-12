package iuh.fit.se.userservice.provider;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class TokenProvider {

    @Bean
    public TokenExtractor tokenExtractor() {
        return request -> {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
            return null;
        };
    }

    public interface TokenExtractor {
        String extract(HttpServletRequest request);
    }
}
