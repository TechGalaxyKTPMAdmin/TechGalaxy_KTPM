package iuh.fit.se.userservice.mapper;

import iuh.fit.se.userservice.dto.request.PermissionRequest;
import iuh.fit.se.userservice.dto.response.PermissionResponse;
import iuh.fit.se.userservice.entities.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(target = "id", ignore = true)
    // Bỏ qua trường id khi tạo mới
    Permission toEntity(PermissionRequest request);

    PermissionResponse toResponse(Permission permission);

    @Mapping(target = "id", ignore = true)
    // Không cập nhật id khi sửa
    void updateEntityFromRequest(PermissionRequest request, @MappingTarget Permission permission);
}
