package iuh.fit.se.userservice.service.impl;

import iuh.fit.se.userservice.client.OrderServiceClient;
import iuh.fit.se.userservice.dto.response.OrderResponse;
import iuh.fit.se.userservice.service.OrderClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderClientServiceImpl implements OrderClientService {

    private final OrderServiceClient orderServiceClient;

    @Override
    public List<OrderResponse> getOrdersByCustomerId(String customerId) {
        try {
            var response = orderServiceClient.getOrdersByCustomerId(customerId);
            System.out.println(response);
            if (response.getBody() != null && response.getBody().getData() != null) {

                return new ArrayList<>(response.getBody().getData());
            }
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}