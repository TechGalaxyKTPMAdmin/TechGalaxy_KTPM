package iuh.fit.se.orderservice.mapper;

import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toOrderFromResponse(OrderResponse orderResponse);

    Order toOrderFromRequest(OrderRequest orderRequest);

    OrderResponse toOrderResponse(Order order);

    OrderRequest toOrderRequest(Order order);
}
