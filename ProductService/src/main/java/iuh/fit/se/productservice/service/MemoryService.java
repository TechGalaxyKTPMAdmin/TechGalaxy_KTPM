package iuh.fit.se.productservice.service;

import iuh.fit.se.productservice.dto.request.MemoryRequest;
import iuh.fit.se.productservice.dto.response.MemoryResponse;

import java.util.List;

public interface MemoryService {
    List<MemoryResponse> getAllMemory();
    MemoryResponse getMemoryById(String id);
    MemoryResponse addMemory(MemoryRequest memoryRequest);
    MemoryResponse updateMemory(String id, MemoryRequest memoryRequest);
    MemoryResponse deleteMemory(String id);
    List<MemoryResponse> getMemoriesByIDs(List<String> ids);
}
