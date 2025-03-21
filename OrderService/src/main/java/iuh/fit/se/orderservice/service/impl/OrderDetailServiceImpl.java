package iuh.fit.se.orderservice.service.impl;

import iuh.fit.se.orderservice.dto.request.OrderCreateRequest;
import iuh.fit.se.orderservice.dto.request.OrderDetailRequest;
import iuh.fit.se.orderservice.dto.response.OrderDetailResponse;
import iuh.fit.se.orderservice.entity.Order;
import iuh.fit.se.orderservice.entity.OrderDetail;
import iuh.fit.se.orderservice.entity.enumeration.DetailStatus;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import iuh.fit.se.orderservice.mapper.OrderDetailMapper;
import iuh.fit.se.orderservice.repository.OrderDetailRepository;
import iuh.fit.se.orderservice.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<OrderDetailResponse> getOrderDetailsByOrderId(String orderId) {
        List<OrderDetail> orderDetails = this.orderDetailRepository.findOrderDetailsByOrderId(orderId);
        return orderDetails.stream()
                .map(OrderDetailMapper.INSTANCE::toOrderDetailResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(List<OrderCreateRequest.ProductDetailRequest> productDetails, Order order) {
        for (OrderCreateRequest.ProductDetailRequest productDetail : productDetails) {// check product
//            if (restTemplate.getForObject('url', ProductDetailResponse.class, productDetail.getProductVariantDetailId()))
//                return false;
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setDetailStatus(DetailStatus.PENDING);
            orderDetail.setQuantity(productDetail.getQuantity());
            orderDetail.setPrice(productDetail.getPrice() * productDetail.getQuantity());
            orderDetail.setProductVariantDetailId(productDetail.getProductVariantDetailId());
            orderDetail.setCreatedAt(LocalDateTime.now());
            orderDetailRepository.save(orderDetail);
        }
        return true;
    }

    @Override
    public OrderDetailResponse findById(String id) {
        return OrderDetailMapper.INSTANCE.toOrderDetailResponse(orderDetailRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOTFOUND)));
    }

    @Override
    public OrderDetailResponse update(String id, OrderDetailRequest orderDetailRequest) {
        return null;
    }

    @Override
    public OrderDetailResponse getOrderDetailByOrderIdAndProductVariantDetailId(String orderId, String productVariantDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailByOrderIdAndProductVariantDetailId(orderId, productVariantDetailId);
        return OrderDetailMapper.INSTANCE.toOrderDetailResponse(orderDetail);
    }
}
