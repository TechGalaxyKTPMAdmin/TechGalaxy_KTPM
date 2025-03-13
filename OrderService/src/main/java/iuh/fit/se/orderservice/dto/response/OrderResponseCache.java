package iuh.fit.se.orderservice.dto.response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderResponseCache {
    private final Map<String, CompletableFuture<PaymentStatusResponse>> cache = new ConcurrentHashMap<>();

    public void put(String orderId, CompletableFuture<PaymentStatusResponse> future) {
        cache.put(orderId, future);
    }

    public CompletableFuture<PaymentStatusResponse> get(String orderId) {
        return cache.get(orderId);
    }

    public void remove(String orderId) {
        cache.remove(orderId);
    }
}
