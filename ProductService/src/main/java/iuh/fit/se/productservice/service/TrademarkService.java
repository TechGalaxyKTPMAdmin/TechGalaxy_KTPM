package iuh.fit.se.productservice.service;

import iuh.fit.se.productservice.dto.response.*;

import java.util.List;

public interface TrademarkService {
    TrademarkResponse createTrademark(String name);

    boolean deleteTrademark(String id);

    TrademarkResponse updateTrademark(String name, String newName);

    TrademarkResponse getTrademarkByName(String name);

    List<TrademarkResponse> getAllTrademarks();

    TrademarkResponse getByID(String id);
}
