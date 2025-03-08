package iuh.fit.se.productservice.mapper;


import iuh.fit.se.productservice.dto.request.AttributeRequest;
import iuh.fit.se.productservice.dto.response.AttributeResponse;
import iuh.fit.se.productservice.entities.Attribute;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface AttributeMapper {
    AttributeResponse toAttributeResponse(Attribute attribute);

    Attribute toAttribute(AttributeRequest attributeRequest);

    void updateAttributeFromRequest(@MappingTarget Attribute attribute, AttributeRequest attributeRequest);
}

