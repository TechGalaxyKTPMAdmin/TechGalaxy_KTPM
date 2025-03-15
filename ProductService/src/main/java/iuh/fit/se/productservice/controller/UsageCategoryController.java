package iuh.fit.se.productservice.controller;

import iuh.fit.se.productservice.dto.request.UsageCategoryRequest;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.dto.response.UsageCategoryResponse;
import iuh.fit.se.productservice.service.impl.UsageCategoryServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/usageCategories")
public class UsageCategoryController {
    UsageCategoryServiceImpl usageCategoryServiceImpl;

    @GetMapping
    public ResponseEntity<DataResponse<UsageCategoryResponse>> getAllUsageCategory() {
        return ResponseEntity.ok(DataResponse.<UsageCategoryResponse>builder()
                .data(usageCategoryServiceImpl.getAllUsageCategories()).build());
    }

    @GetMapping("/usageCategories/{id}")
    public ResponseEntity<DataResponse<UsageCategoryResponse>> getUsageCategoryById(@PathVariable String id) {
        Set<UsageCategoryResponse> usageCategoryResponse = new HashSet<UsageCategoryResponse>();
        usageCategoryResponse.add(usageCategoryServiceImpl.getUsageCategoryById(id));
        return ResponseEntity.ok(DataResponse.<UsageCategoryResponse>builder().data(usageCategoryResponse).build());
    }

    @PostMapping
    public ResponseEntity<DataResponse<UsageCategoryResponse>> createUsageCategory(
            @RequestBody UsageCategoryRequest usageCategoryRequest) {
        Set<UsageCategoryResponse> usageCategoryResponse = new HashSet<UsageCategoryResponse>();
        usageCategoryResponse.add(usageCategoryServiceImpl.createUsageCategory(usageCategoryRequest));
        return ResponseEntity.ok(DataResponse.<UsageCategoryResponse>builder().data(usageCategoryResponse).build());
    }

    @PutMapping("/usageCategories/{id}")
    public ResponseEntity<DataResponse<UsageCategoryResponse>> updateUsageCategory(@PathVariable String id,
                                                                                   @RequestBody UsageCategoryRequest usageCategoryRequest) {
        Set<UsageCategoryResponse> usageCategoryResponse = new HashSet<UsageCategoryResponse>();
        usageCategoryResponse.add(usageCategoryServiceImpl.updateUsageCategory(id, usageCategoryRequest));
        return ResponseEntity.ok(DataResponse.<UsageCategoryResponse>builder().data(usageCategoryResponse).build());
    }

    @DeleteMapping("/usageCategories/{id}")
    public ResponseEntity<DataResponse<Object>> deleteUsageCategory(@PathVariable String id) {
        usageCategoryServiceImpl.deleteUsageCategory(id);
        return ResponseEntity.ok(DataResponse.<Object>builder().message("Delete " + id + " success").build());
    }

}
