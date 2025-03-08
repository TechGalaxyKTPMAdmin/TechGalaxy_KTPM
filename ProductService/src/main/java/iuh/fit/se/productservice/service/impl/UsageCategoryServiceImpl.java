package iuh.fit.se.productservice.service.impl;

import iuh.fit.se.productservice.dto.request.UsageCategoryRequest;
import iuh.fit.se.productservice.dto.response.UsageCategoryResponse;
import iuh.fit.se.productservice.entities.UsageCategory;
import iuh.fit.se.productservice.mapper.UsageCategoryMapper;
import iuh.fit.se.productservice.repository.UsageCategoryRepository;
import iuh.fit.se.productservice.service.UsageCategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsageCategoryServiceImpl implements UsageCategoryService {

    UsageCategoryRepository usageCategoryRepository;
    UsageCategoryMapper usageCategoryMapper;

    @Override
    public List<UsageCategoryResponse> getAllUsageCategories() {
        return usageCategoryRepository.findAll().stream().map(usageCategoryMapper::toUseCategoryResponse).toList();
    }

    @Override
    public UsageCategoryResponse getUsageCategoryById(String id) {
        return usageCategoryRepository.findById(id).map(usageCategoryMapper::toUseCategoryResponse)
                .orElseThrow(() -> new RuntimeException("UsageCategory not found"));

    }

    @Override
    public UsageCategoryResponse createUsageCategory(UsageCategoryRequest usageCategoryRequest) {
        UsageCategory usageCategory = usageCategoryMapper.toUsageCategory(usageCategoryRequest);
        return usageCategoryMapper.toUseCategoryResponse(usageCategoryRepository.save(usageCategory));

    }

    @Override
    public UsageCategoryResponse updateUsageCategory(String id, UsageCategoryRequest usageCategoryRequest) {
        UsageCategory usageCategory = usageCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("usageCategory not found"));
        usageCategoryMapper.updateUsageCategoryFromRequest(usageCategory, usageCategoryRequest);
        return usageCategoryMapper.toUseCategoryResponse(usageCategoryRepository.save(usageCategory));

    }

    @Override
    public void deleteUsageCategory(String id) {
        UsageCategory usageCategory = usageCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("usageCategory not found"));
        usageCategoryRepository.delete(usageCategory);

    }
}
