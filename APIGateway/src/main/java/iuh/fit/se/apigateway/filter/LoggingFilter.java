package iuh.fit.se.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();

        log.info("Incoming request - Method: {}, Path: {}", method, path);

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    System.out.println("Request completed - Method: " + method + ", Path: " + path + ", Duration: "
                            + duration + "ms, Status: " + exchange.getResponse().getStatusCode());
                    log.info("Request completed - Method: {}, Path: {}, Duration: {}ms, Status: {}",
                            method, path, duration, exchange.getResponse().getStatusCode());
                }));
    }
}