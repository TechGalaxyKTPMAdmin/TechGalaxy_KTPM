package iuh.fit.se.productservice.service.impl;

import iuh.fit.se.productservice.dto.request.MemoryRequest;
import iuh.fit.se.productservice.dto.response.MemoryResponse;
import iuh.fit.se.productservice.entities.Memory;
import iuh.fit.se.productservice.mapper.MemoryMapper;
import iuh.fit.se.productservice.repository.MemoryRepository;
import iuh.fit.se.productservice.service.MemoryService;
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
public class MemoryServiceImpl implements MemoryService {

    MemoryRepository memoryRepository;
    MemoryMapper memoryMapper;


    @Override
    public List<MemoryResponse> getAllMemory() {
        return memoryRepository.findAll().stream().map(memoryMapper::memoryToMemoryResponse).toList();
    }

    @Override
    public MemoryResponse getMemoryById(String id) {
        Memory memory = memoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Memory not found"));
        return memoryMapper.memoryToMemoryResponse(memory);
    }

    @Override
    public MemoryResponse addMemory(MemoryRequest memoryRequest) {
        Memory memory = memoryMapper.toMemory(memoryRequest);
        return memoryMapper.memoryToMemoryResponse(memoryRepository.save(memory));
    }

    @Override
    public MemoryResponse updateMemory(String id,MemoryRequest memoryRequest) {
        Memory memory = memoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Color not found"));
        memoryMapper.updateMemory(memory, memoryRequest);
        return memoryMapper.memoryToMemoryResponse(memoryRepository.save(memory));
    }

    @Override
    public MemoryResponse deleteMemory(String id) {
        Memory memory = memoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Memory not found"));

        MemoryResponse memoryResponse = new MemoryResponse(memory.getId(), memory.getName(), memory.getCreatedAt(),memory.getUpdatedAt());

        memoryRepository.delete(memory);
        return memoryResponse;
    }


    @Override
    public List<MemoryResponse> getMemoriesByIDs(List<String> ids) {
        return memoryRepository.findMemoriesByIdIsIn(ids).stream().map(memoryMapper::memoryToMemoryResponse).toList();
    }
}
