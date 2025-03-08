package iuh.fit.se.productservice.mapper;


import iuh.fit.se.productservice.dto.request.UsageCategoryRequest;
import iuh.fit.se.productservice.dto.response.UsageCategoryResponse;
import iuh.fit.se.productservice.entities.UsageCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsageCategoryMapper {
    UsageCategoryResponse toUseCategoryResponse(UsageCategory usageCategory);

    UsageCategory toUsageCategory(UsageCategoryRequest usageCategoryRequest);

    void updateUsageCategoryFromRequest(@MappingTarget UsageCategory usageCategory, UsageCategoryRequest usageCategoryRequest);

}
