package iuh.fit.se.productservice.controller;

import iuh.fit.se.productservice.dto.request.MemoryRequest;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.dto.response.MemoryResponse;
import iuh.fit.se.productservice.service.impl.MemoryServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/memories")
public class MemoryController {

    MemoryServiceImpl memoryServiceImpl;

    @GetMapping
    public ResponseEntity<DataResponse<MemoryResponse>> getAllMemory() {
        return ResponseEntity.ok(
                DataResponse.<MemoryResponse>builder()
                        .data(memoryServiceImpl.getAllMemory())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<DataResponse<MemoryResponse>> createMemory(@RequestBody MemoryRequest request) {
        Set<MemoryResponse> memoryResponses = new HashSet<>();
        memoryResponses.add(memoryServiceImpl.addMemory(request));
        return ResponseEntity.ok(DataResponse.<MemoryResponse>builder().data(memoryResponses).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<MemoryResponse>> updateMemory(@PathVariable String id, @RequestBody MemoryRequest request) {
        Set<MemoryResponse> memoryResponses = new HashSet<>();
        memoryResponses.add(memoryServiceImpl.updateMemory(id, request));
        return ResponseEntity.ok(DataResponse.<MemoryResponse>builder().data(memoryResponses).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<MemoryResponse>> deleteMemory(@PathVariable String id) {
        Set<MemoryResponse> memoryResponses = new HashSet<>();
        memoryResponses.add(memoryServiceImpl.deleteMemory(id));
        return ResponseEntity.ok(DataResponse.<MemoryResponse>builder().data(memoryResponses).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<MemoryResponse>> getMemoryById(String id) {
        return ResponseEntity.ok(DataResponse.<MemoryResponse>builder().data(List.of( memoryServiceImpl.getMemoryById(id))).build());
    }

    @GetMapping("/ids")
    public ResponseEntity<DataResponse<MemoryResponse>> getMemoriesByIDs(@RequestParam List<String> ids) {
        return ResponseEntity.ok(DataResponse.<MemoryResponse>builder().data(memoryServiceImpl.getMemoriesByIDs(ids)).build());
    }
}
