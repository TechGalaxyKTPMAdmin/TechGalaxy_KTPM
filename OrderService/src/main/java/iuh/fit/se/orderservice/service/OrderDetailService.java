package iuh.fit.se.orderservice.service;

import iuh.fit.se.orderservice.dto.request.OrderDetailRequest;
import iuh.fit.se.orderservice.dto.response.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailResponse> getOrderDetailsByOrderId(String orderId);

    OrderDetailResponse save(OrderDetailRequest orderDetailRequest);

    OrderDetailResponse findById(String id);

    OrderDetailResponse update(String id, OrderDetailRequest orderDetailRequest);

    OrderDetailResponse getOrderDetailByOrderIdAndProductVariantDetailId(String orderId, String productVariantDetailId);
}
