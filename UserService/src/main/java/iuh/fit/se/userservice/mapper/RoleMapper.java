package iuh.fit.se.userservice.mapper;

import iuh.fit.se.userservice.dto.request.RoleRequest;
import iuh.fit.se.userservice.dto.response.RoleResponse;
import iuh.fit.se.userservice.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    /**
     * Convert Request to Entity
     *
     * @param request
     * @return Role
     *         author: Vu Nguyen Minh Duc
     */
    Role toEntity(RoleRequest request);

    /**
     * Convert Response to Entity
     *
     * @param response
     * @return Role
     *         author: Vu Nguyen Minh Duc
     */
    Role toEntity(RoleResponse response);

    /**
     * Convert Entity to Response
     *
     * @param role
     * @return RoleResponse
     *         author: Vu Nguyen Minh Duc
     */
    RoleResponse toResponse(Role role);

    /**
     * Convert Entity to Request
     *
     * @param role
     * @return RoleRequest
     *         author: Vu Nguyen Minh Duc
     */
    RoleRequest toRequest(Role role);

}