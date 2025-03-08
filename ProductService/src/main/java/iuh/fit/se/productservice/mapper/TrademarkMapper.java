package iuh.fit.se.productservice.mapper;

import iuh.fit.se.productservice.dto.request.TrademarkRequest;
import iuh.fit.se.productservice.dto.response.TrademarkResponse;
import iuh.fit.se.productservice.entities.Trademark;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrademarkMapper {
    TrademarkMapper INSTANCE = Mappers.getMapper(TrademarkMapper.class);

    TrademarkResponse toTrademarkResponse(Trademark trademark);

    TrademarkRequest toTrademarkRequest(Trademark trademark);

    Trademark toTrademarkFromRequest(TrademarkRequest trademarkRequest);

    Trademark toTrademarkFromResponse(TrademarkResponse trademarkResponse);
}
