package iuh.fit.se.orderservice.service.impl;

import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.request.OrderRequestV2;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.entity.*;
import iuh.fit.se.orderservice.entity.enumeration.DetailStatus;
import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import iuh.fit.se.orderservice.mapper.OrderMapper;
import iuh.fit.se.orderservice.repository.*;
import iuh.fit.se.orderservice.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    CustomerRepository customerRepository;
    OrderDetailRepository orderDetailRepository;
    ProductVariantDetailRepository productVariantDetailRepository;
    SystemUserRepository systemUserRepository;

    /**
     * Save order
     *
     * @param orderRequest
     * @return OrderResponse
     * author: PhamVanThanh
     */
    @Override
    public OrderResponse save(OrderRequest orderRequest) {
        Order order = orderRepository.save(OrderMapper.INSTANCE.toOrderFromRequest(orderRequest));
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    /**
     * Find order by id
     *
     * @param id
     * @return OrderResponse
     * author: PhamVanThanh
     */
    @Override
    public OrderResponse findById(String id) {
        Order order = orderRepository.findById(id).orElse(null);
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    /**
     * Update order
     *
     * @param orderRequest
     * @return OrderResponse
     * author: PhamVanThanh
     */
    @Override
    public OrderResponse update(String id, OrderRequest orderRequest) {
        if (!orderRepository.existsById(id))
            return null;
        Order order = orderRepository.save(OrderMapper.INSTANCE.toOrderFromRequest(orderRequest));
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    /**
     * Find all orders with pagination
     *
     * @param page
     * @param size
     * @return PagedModel<OrderResponse>
     * author: PhamVanThanh
     */
    @Override
    public PagedModel<OrderResponse> findAllOrders(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageRequest);

        List<OrderResponse> orderResponses = orderPage.getContent()
                .stream()
                .map(OrderMapper.INSTANCE::toOrderResponse)
                .collect(Collectors.toList());
        return PagedModel.of(
                orderResponses,
                new PagedModel.PageMetadata(
                        orderPage.getSize(),
                        orderPage.getNumber(),
                        orderPage.getTotalElements()
                )
        );
    }

    @Override
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper.INSTANCE::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> findOrdersByCustomerId(String id) {
        return orderRepository.getOrdersByCustomerId(id)
                .stream()
                .map(OrderMapper.INSTANCE::toOrderResponse)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public OrderResponse createOrders(OrderRequestV2 orderRequestV2) {
        Customer customer = customerRepository.findById(orderRequestV2.getCustomerId()).orElseThrow(
                () -> new AppException(ErrorCode.CUSTOMER_NOTFOUND)
        );

        Order.OrderBuilder orderBuilder = Order.builder()
                .customer(customer)
                .paymentStatus(orderRequestV2.getPaymentStatus())
                .address(orderRequestV2.getAddress())
                .orderStatus(OrderStatus.NEW);
        if (orderRequestV2.getSystemUserId() != null && !orderRequestV2.getSystemUserId().isEmpty()) {
            SystemUser systemUser = systemUserRepository.findById(orderRequestV2.getSystemUserId()).orElseThrow(
                    () -> new AppException(ErrorCode.SYSTEM_USER_NOTFOUND)
            );
            orderBuilder.orderStatus(OrderStatus.COMPLETED)
                    .systemUser(systemUser);
        }
        Order order = orderBuilder.build();
        orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderRequestV2.ProductDetailOrder productDetail : orderRequestV2.getProductDetailOrders()) {
            ProductVariantDetail productVariantDetail = productVariantDetailRepository.findById(productDetail.getProductVariantDetailId()).orElseThrow(
                    () -> new AppException(ErrorCode.PRODUCT_NOTFOUND)
            );

            if (productVariantDetail.getQuantity() < productDetail.getQuantity()){
                throw new AppException(ErrorCode.INSUFFICIENT_PRODUCT_QUANTITY, "Product: " + productVariantDetail.getProductVariant().getName() + " - Quantity: " + productVariantDetail.getQuantity());
            }
            if (productDetail.getQuantity() <= 0){
                throw new AppException(ErrorCode.INVALID_PRODUCT_QUANTITY, "Product quantity: " + productDetail.getQuantity());
            }
            double discountedPrice = (1 - productVariantDetail.getSale()) * productVariantDetail.getPrice();
            double totalPrice = discountedPrice * productDetail.getQuantity();

            orderDetails.add(OrderDetail.builder()
                    .order(order)
                    .detailStatus(DetailStatus.PROCESSING)
                    .productVariantDetail(productVariantDetail)
                    .quantity(productDetail.getQuantity())
                    .price(totalPrice)
                    .build());
        }

        orderDetailRepository.saveAll(orderDetails);

        // Cập nhật số lượng sản phẩm trong kho sau khi lưu tất cả OrderDetails
        for (OrderRequestV2.ProductDetailOrder productDetail : orderRequestV2.getProductDetailOrders()) {
            ProductVariantDetail productVariantDetail = productVariantDetailRepository.findById(productDetail.getProductVariantDetailId()).orElseThrow(
                    () -> new AppException(ErrorCode.PRODUCT_NOTFOUND)
            );
            productVariantDetail.setQuantity(productVariantDetail.getQuantity() - productDetail.getQuantity());
            productVariantDetailRepository.save(productVariantDetail);
        }

        OrderResponse orderResponse = OrderMapper.INSTANCE.toOrderResponse(order);

        return orderResponse;
    }


}
