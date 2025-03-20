package iuh.fit.se.productservice.mapper;

import iuh.fit.se.productservice.dto.request.ColorRequest;
import iuh.fit.se.productservice.dto.request.MemoryRequest;
import iuh.fit.se.productservice.dto.response.MemoryResponse;
import iuh.fit.se.productservice.entities.Color;
import iuh.fit.se.productservice.entities.Memory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MemoryMapper {
    MemoryResponse memoryToMemoryResponse(Memory memory);
    Memory toMemory(MemoryRequest memoryRequest);

    void updateMemory(@MappingTarget Memory memory, MemoryRequest memoryRequest);
}
