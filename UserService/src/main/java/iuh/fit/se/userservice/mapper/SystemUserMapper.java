package iuh.fit.se.userservice.mapper;

import iuh.fit.se.userservice.dto.request.SystemUserRequestDTO;
import iuh.fit.se.userservice.dto.response.SystemUserResponseDTO;
import iuh.fit.se.userservice.entities.SystemUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SystemUserMapper {
    SystemUserMapper INSTANCE = Mappers.getMapper(SystemUserMapper.class);

    SystemUser toEntity(SystemUserRequestDTO userRequestDTO);

    SystemUserResponseDTO toResponseDTO(SystemUser systemUser);

}
