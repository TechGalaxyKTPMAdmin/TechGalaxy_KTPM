package iuh.fit.se.userservice.service;

import iuh.fit.se.userservice.dto.request.SystemUserRequestDTO;
import iuh.fit.se.userservice.dto.response.ResultPaginationDTO;
import iuh.fit.se.userservice.dto.response.SystemUserResponseDTO;
import iuh.fit.se.userservice.entities.SystemUser;
import iuh.fit.se.userservice.entities.enumeration.SystemUserStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface SystemUserService {

    /**
     * Handle create user
     *
     * @param user
     * @return User
     * author: Vũ Nguyễn Minh Đức
     */
    SystemUserResponseDTO handleCreateSystemUser(SystemUserRequestDTO user);

    /**
     * Handle delete user
     *
     * @param id author: Vũ Nguyễn Minh Đức
     */
    void handleDeleteSystemUser(String id);


    /**
     * Fetch user by id
     *
     * @param id
     * @return User
     * author: Vũ Nguyễn Minh Đức
     */
    SystemUserResponseDTO fetchUserById(String id);

    List<SystemUserResponseDTO> fetchAllSystemUser();

    /**
     * Fetch all user
     *
     * @param spec
     * @param pageable
     * @return ResultPaginationDTO
     * author: Vũ Nguyễn Minh Đức
     */
    ResultPaginationDTO fetchAllSystemUser(Specification<SystemUser> spec, Pageable pageable);

    /**
     * Handle update user
     *
     * @param reqUser
     * @return User
     * author: Vũ Nguyễn Minh Đức
     */
    SystemUserResponseDTO handleUpdateSystemUser(SystemUserRequestDTO reqUser);

    /**
     * Check if email exist
     *
     * @param email
     * @return boolean
     * author: Vũ Nguyễn Minh Đức
     */
    boolean isEmailExist(String email);

    /**
     * Handle get user by status
     *
     * @param status
     * @return List<SystemUser>
     * author: Vũ Nguyễn Minh Đức
     */
    List<SystemUserResponseDTO> handleGetSystemUsersByStatus(SystemUserStatus status);

    SystemUserResponseDTO findSystemUserByEmail(String email);
}
