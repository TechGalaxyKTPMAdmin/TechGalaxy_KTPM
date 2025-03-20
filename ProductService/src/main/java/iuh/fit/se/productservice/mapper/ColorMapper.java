package iuh.fit.se.productservice.mapper;

import iuh.fit.se.productservice.dto.request.ColorRequest;
import iuh.fit.se.productservice.dto.response.ColorResponse;
import iuh.fit.se.productservice.entities.Color;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorResponse toColorResponse(Color color);
    Color toColor(ColorRequest colorRequest);

    void updateColor(@MappingTarget Color color, ColorRequest colorRequest);
}
