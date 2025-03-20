package iuh.fit.se.productservice.service;

import iuh.fit.se.productservice.dto.request.ColorRequest;
import iuh.fit.se.productservice.dto.response.ColorResponse;
import iuh.fit.se.productservice.entities.Color;

import java.util.List;

public interface ColorService {
    List<ColorResponse> getAllColors();

    ColorResponse getColorById(String colorId);

    ColorResponse createColor(ColorRequest colorRequest);

    ColorResponse updateColor(String colorId, ColorRequest colorRequest);

    ColorResponse deleteColor(String colorId);

    List<ColorResponse> getColorsByIDs(List<String> ids);


}
