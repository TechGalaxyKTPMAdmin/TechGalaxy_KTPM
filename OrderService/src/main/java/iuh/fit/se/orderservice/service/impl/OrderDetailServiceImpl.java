package iuh.fit.se.orderservice.service.impl;

import iuh.fit.se.orderservice.dto.request.OrderDetailRequest;
import iuh.fit.se.orderservice.dto.response.OrderDetailResponse;
import iuh.fit.se.orderservice.entities.OrderDetail;
import iuh.fit.se.orderservice.entities.ProductVariantDetail;
import iuh.fit.se.orderservice.entities.enumeration.OrderStatus;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import iuh.fit.se.orderservice.mapper.OrderDetailMapper;
import iuh.fit.se.orderservice.mapper.ProductMapper;
import iuh.fit.se.orderservice.mapper.ProductVariantDetailMapper;
import iuh.fit.se.orderservice.repository.OrderDetailRepository;
import iuh.fit.se.orderservice.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ProductMapper productMapper;
    private final ProductVariantDetailMapper productVariantDetailMapper;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, ProductMapper productMapper, ProductVariantDetailMapper productVariantDetailMapper) {
        this.orderDetailRepository = orderDetailRepository;
        this.productMapper = productMapper;
        this.productVariantDetailMapper = productVariantDetailMapper;
    }

    /**
     * Get order details by order id
     *
     * @param orderId
     * @return List<OrderDetailResponse>
     * author: PhamVanThanh
     */
    @Override
    public List<OrderDetailResponse> getOrderDetailsByOrderId(String orderId) {
        List<OrderDetail> orderDetails = this.orderDetailRepository.findOrderDetailsByOrderId(orderId);
        return orderDetails.stream()
                .map(OrderDetailMapper.INSTANCE::toOrderDetailResponse)
                .collect(Collectors.toList());
    }

    /**
     * Save order detail
     *
     * @param orderDetailRequest
     * @return OrderDetailResponse
     * author: PhamVanThanh
     */
    @Override
    public OrderDetailResponse save(OrderDetailRequest orderDetailRequest) {
        OrderDetail orderDetail = orderDetailRepository.save(OrderDetailMapper.INSTANCE.toOrderDetailFromRequest(orderDetailRequest));
        return OrderDetailMapper.INSTANCE.toOrderDetailResponse(orderDetail);
    }


    /**
     * Find order detail by id
     *
     * @param id
     * @return OrderDetailResponse
     * author: PhamVanThanh
     */
    @Override
    public OrderDetailResponse findById(String id) {
        return OrderDetailMapper.INSTANCE.toOrderDetailResponse(orderDetailRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOTFOUND)));
    }

    @Override
    public OrderDetailResponse update(String id, OrderDetailRequest orderDetailRequest) {
        if (orderDetailRequest.getOrder().getOrderStatus() != OrderStatus.NEW && orderDetailRequest.getOrder().getOrderStatus() != OrderStatus.PROCESSING) {
            throw new AppException(ErrorCode.NOT_UPDATE_ORDER);
        }
        return orderDetailRepository.findById(id)
                .map(orderDetail -> {
                    ProductVariantDetail productVariantDetail = new ProductVariantDetail();
                    productVariantDetail.setId(orderDetailRequest.getProductVariantDetail().getId());
                    orderDetail.setQuantity(orderDetailRequest.getQuantity());
                    orderDetail.setProductVariantDetail(productVariantDetail);
                    return OrderDetailMapper.INSTANCE.toOrderDetailResponse(orderDetailRepository.save(orderDetail));
                })
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOTFOUND));
    }

    @Override
    public OrderDetailResponse getOrderDetailByOrderIdAndProductVariantDetailId(String orderId, String productVariantDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailByOrderIdAndProductVariantDetailId(orderId, productVariantDetailId);
        return OrderDetailMapper.INSTANCE.toOrderDetailResponse(orderDetail);
    }
}
