package iuh.fit.se.userservice.service;

import iuh.fit.se.userservice.dto.request.PermissionRequest;
import iuh.fit.se.userservice.dto.response.PermissionResponse;
import iuh.fit.se.userservice.dto.response.ResultPaginationDTO;
import iuh.fit.se.userservice.entities.Permission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface PermissionService {

    /**
     * Check if permission exists
     *
     * @param request
     * @return boolean
     * author: Vu Nguyen Minh Duc
     */
    boolean isPermissionExist(PermissionRequest request);

    /**
     * Fetch permission by id
     *
     * @param id
     * @return PermissionResponse
     * author: Vu Nguyen Minh Duc
     */
    PermissionResponse fetchById(String id);

    /**
     * Create new permission
     *
     * @param request
     * @return PermissionResponse
     * author: Vu Nguyen Minh Duc
     */
    PermissionResponse create(PermissionRequest request);

    /**
     * Update permission
     *
     * @param request
     * @return PermissionResponse
     * author: Vu Nguyen Minh Duc
     */
    PermissionResponse update(PermissionRequest request);

    /**
     * Delete permission
     *
     * @param id
     */
    void delete(String id);

    /**
     * Get all permissions
     *
     * @param spec
     * @param pageable
     * @return ResultPaginationDTO
     * author: Vu Nguyen Minh Duc
     */
    ResultPaginationDTO getPermissions(Specification<Permission> spec, Pageable pageable);

    /**
     * Check if permission has same name
     *
     * @param request
     * @return boolean
     * author: Vu Nguyen Minh Duc
     */
    boolean isSameName(PermissionRequest request);
}
