package iuh.fit.se.orderservice.mapper;

import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toOrderFromResponse(OrderResponse orderResponse);

    Order toOrderFromRequest(OrderRequest orderRequest);

    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(source = "systemUserId", target = "systemUser.id")
    @Mapping(source = "orderDetails", target = "orderDetails")
    OrderResponse toOrderResponse(Order order);

    OrderRequest toOrderRequest(Order order);
}
