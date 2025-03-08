package iuh.fit.se.productservice.service.impl;

import iuh.fit.se.productservice.dto.response.TrademarkResponse;
import iuh.fit.se.productservice.entities.Trademark;
import iuh.fit.se.productservice.exception.AppException;
import iuh.fit.se.productservice.exception.ErrorCode;
import iuh.fit.se.productservice.mapper.TrademarkMapper;
import iuh.fit.se.productservice.repository.TrademarkRepository;
import iuh.fit.se.productservice.service.TrademarkService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrademarkServiceImpl implements TrademarkService {
    TrademarkRepository trademarkRepository;

    @Override
    public TrademarkResponse createTrademark(String name) {
        Trademark trademark = new Trademark();
        trademark.setName(name);
        return TrademarkMapper.INSTANCE.toTrademarkResponse(trademarkRepository.save(trademark));
    }

    @Override
    public boolean deleteTrademark(String id) {

        Trademark trademark = trademarkRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRADEMARK_NOTFOUND));
        long productCount = trademarkRepository.countProductsByTrademarkId(id);

        if (productCount > 0) {
            throw new IllegalStateException("Cannot delete category as it contains products.");
        }
        if (trademark != null) {
            trademarkRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public TrademarkResponse updateTrademark(String id, String newName) {
        Trademark trademark = trademarkRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRADEMARK_NOTFOUND));
        if (trademark != null) {
            trademark.setName(newName);
            return TrademarkMapper.INSTANCE.toTrademarkResponse(trademarkRepository.save(trademark));
        }
        return null;

    }

    @Override
    public TrademarkResponse getTrademarkByName(String name) {
        Trademark trademark = trademarkRepository.findTrademarkByName(name);
        return TrademarkMapper.INSTANCE.toTrademarkResponse(trademark);

    }

    @Override
    public List<TrademarkResponse> getAllTrademarks() {
        List<Trademark> trademarks = trademarkRepository.findAll();
        return trademarks.stream().map(TrademarkMapper.INSTANCE::toTrademarkResponse).collect(Collectors.toList());
    }

    @Override
    public TrademarkResponse getByID(String id) {
        Trademark trademark = trademarkRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRADEMARK_NOTFOUND));
        return TrademarkMapper.INSTANCE.toTrademarkResponse(trademark);
    }

}
