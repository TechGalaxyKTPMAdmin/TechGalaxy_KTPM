package iuh.fit.se.orderservice.service;

import iuh.fit.se.orderservice.dto.request.OrderRequestV2;
import iuh.fit.se.orderservice.dto.request.OrderDetailRequest;
import iuh.fit.se.orderservice.dto.response.OrderDetailResponse;
import iuh.fit.se.orderservice.entity.Order;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailResponse> getOrderDetailsByOrderId(String orderId);

    boolean save(List<OrderRequestV2.ProductDetailOrder> productDetails, Order order);

    OrderDetailResponse findById(String id);

    OrderDetailResponse update(String id, OrderDetailRequest orderDetailRequest);

    OrderDetailResponse getOrderDetailByOrderIdAndProductVariantDetailId(String orderId, String productVariantDetailId);
}
