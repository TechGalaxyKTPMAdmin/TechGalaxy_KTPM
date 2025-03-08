package iuh.fit.se.productservice.service;

import iuh.fit.se.productservice.dto.request.UsageCategoryRequest;
import iuh.fit.se.productservice.dto.response.UsageCategoryResponse;

import java.util.List;

public interface UsageCategoryService {

    List<UsageCategoryResponse> getAllUsageCategories();

    UsageCategoryResponse getUsageCategoryById(String id);

    UsageCategoryResponse createUsageCategory(UsageCategoryRequest usageCategoryRequest);

    UsageCategoryResponse updateUsageCategory(String id, UsageCategoryRequest usageCategoryRequest);

    void deleteUsageCategory(String id);
}
