package iuh.fit.se.productservice.service.impl;

import iuh.fit.se.productservice.dto.request.ColorRequest;
import iuh.fit.se.productservice.dto.response.ColorResponse;
import iuh.fit.se.productservice.entities.Color;
import iuh.fit.se.productservice.entities.ProductVariantDetail;
import iuh.fit.se.productservice.mapper.ColorMapper;
import iuh.fit.se.productservice.repository.ColorRepository;
import iuh.fit.se.productservice.repository.ProductVariantDetailRepository;
import iuh.fit.se.productservice.service.ColorService;
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
public class ColorServiceImpl implements ColorService {

    ColorRepository colorRepository;
    ColorMapper colorMapper;
    ProductVariantDetailRepository productVariantDetailRepository;

    @Override
    public List<ColorResponse> getAllColors() {
        return colorRepository.findAll().stream().map(colorMapper::toColorResponse).toList();
    }

    @Override
    public ColorResponse getColorById(String colorId) {
        return  colorRepository.findById(colorId).map(colorMapper::toColorResponse).orElseThrow(() -> new RuntimeException("Color not found"));
    }

    @Override
    public ColorResponse createColor(ColorRequest colorRequest) {
        Color color = colorMapper.toColor(colorRequest);
        return colorMapper.toColorResponse(colorRepository.save(color));
    }

    @Override
    public ColorResponse updateColor(String colorId, ColorRequest colorRequest) {
        Color color = colorRepository.findById(colorId).orElseThrow(() -> new RuntimeException("Color not found"));
        colorMapper.updateColor(color, colorRequest);
        return colorMapper.toColorResponse(colorRepository.save(color));
    }

    @Override
    public ColorResponse deleteColor(String colorId) {
        // Tìm Color theo colorId
        Color color = colorRepository.findById(colorId)
                .orElseThrow(() -> new RuntimeException("Color not found"));
        ColorResponse colorResponse = new ColorResponse(color.getId(), color.getName(),color.getCreatedAt(),color.getUpdatedAt()); // Điều chỉnh theo thuộc tính của Color
        colorRepository.delete(color);
        return colorResponse;
    }


}
