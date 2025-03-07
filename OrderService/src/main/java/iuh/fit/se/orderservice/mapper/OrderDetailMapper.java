package iuh.fit.se.orderservice.mapper;

import iuh.fit.se.orderservice.dto.request.OrderDetailRequest;
import iuh.fit.se.orderservice.dto.response.OrderDetailResponse;
import iuh.fit.se.orderservice.entities.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

//    @Mapping(target = "productVariantDetail.name", source = "orderDetail.productVariantDetail.productVariant.name")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    OrderDetailRequest toOrderDetailRequest(OrderDetail orderDetail);

    OrderDetail toOrderDetailFromRequest(OrderDetailRequest orderDetailRequest);

    OrderDetail toOrderDetailFromResponse(OrderDetailResponse orderDetailResponse);
}
