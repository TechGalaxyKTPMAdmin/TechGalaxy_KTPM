package iuh.fit.se.apigateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // Quan trọng để hiểu lb://
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
