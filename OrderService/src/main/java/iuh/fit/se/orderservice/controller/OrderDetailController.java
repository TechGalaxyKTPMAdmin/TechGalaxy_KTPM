package iuh.fit.se.orderservice.controller;

import iuh.fit.se.orderservice.dto.request.OrderCreateRequest;
import iuh.fit.se.orderservice.dto.request.OrderDetailRequest;
import iuh.fit.se.orderservice.dto.response.DataResponse;
import iuh.fit.se.orderservice.dto.response.OrderDetailResponse;
import iuh.fit.se.orderservice.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<OrderDetailResponse>> getById(@PathVariable String id) {
        List<OrderDetailResponse> orderDetailResponses = List.of(orderDetailService.findById(id));
        return ResponseEntity.ok(DataResponse.<OrderDetailResponse>builder()
                .message("Get order detail by id success")
                .data(orderDetailResponses)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<OrderDetailResponse>> updateOrderDetail(@PathVariable String id, @RequestBody OrderDetailRequest orderDetailRequest) {
        List<OrderDetailResponse> orderDetailResponses = List.of(orderDetailService.update(id, orderDetailRequest));
        return ResponseEntity.ok(DataResponse.<OrderDetailResponse>builder()
                .message("Update order detail success")
                .data(orderDetailResponses)
                .build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DataResponse<OrderDetailResponse>> getOrderDetailsByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok(DataResponse.<OrderDetailResponse>builder()
                .message("Get order details by order id success")
                .data(orderDetailService.getOrderDetailsByOrderId(orderId))
                .build());
    }

    @GetMapping("/order/{orderId}/product-variant-detail/{productVariantDetailId}")
    public ResponseEntity<DataResponse<OrderDetailResponse>> getOrderDetailByOrderIdAndProductVariantDetailId(@PathVariable String orderId, @PathVariable String productVariantDetailId) {
        return ResponseEntity.ok(DataResponse.<OrderDetailResponse>builder()
                .message("Get order detail by order id and product variant detail id success")
                .data(List.of(orderDetailService.getOrderDetailByOrderIdAndProductVariantDetailId(orderId, productVariantDetailId)))
                .build());
    }
}
