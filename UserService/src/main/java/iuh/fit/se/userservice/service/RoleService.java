package iuh.fit.se.userservice.service;

import iuh.fit.se.userservice.dto.request.RoleRequest;
import iuh.fit.se.userservice.dto.response.ResultPaginationDTO;
import iuh.fit.se.userservice.dto.response.RoleResponse;
import iuh.fit.se.userservice.entities.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface RoleService {
    /**
     * Check if role exists by name
     *
     * @param name
     * @return boolean
     * author: Vũ Nguyễn Minh Đức
     */
    boolean existsByName(String name);

    /**
     * Find role by name
     *
     * @param name
     * @return RoleResponse
     * author: Vũ Nguyễn Minh Đức
     */
    RoleResponse findByName(String name);

    /**
     * Create new role
     *
     * @param request
     * @return RoleResponse
     * author: Vũ Nguyễn Minh Đức
     */
    RoleResponse create(RoleRequest request);


    /**
     * Fetch role by id
     *
     * @param id
     * @return RoleResponse
     * author: Vũ Nguyễn Minh Đức
     */
    RoleResponse fetchById(String id);


    /**
     * Update role
     *
     * @param request
     * @return Role
     * author: Vũ Nguyễn Minh Đức
     */
    RoleResponse update(RoleRequest request);

    /**
     * Delete role
     *
     * @param id author: Vũ Nguyễn Minh Đức
     */
    void delete(String id);

    /**
     * Get all roles
     *
     * @param spec
     * @param pageable
     * @return ResultPaginationDTO
     * author: Vũ Nguyễn Minh Đức
     */
    ResultPaginationDTO getRoles(Specification<Role> spec, Pageable pageable);


    //findByNameIn

    /**
     * Find roles by name in list
     *
     * @param names
     * @return List<Role>
     * author: Vũ Nguyễn Minh Đức
     */
    List<Role> findByNameIn(List<String> names);

    List<RoleResponse> findAll();

    /**
     * Fetch role by email
     *
     * @param email
     * @return List<RoleResponse>
     * author: Vũ Nguyễn Minh Đức
     */
    List<RoleResponse> fechByEmail(String email);
}
