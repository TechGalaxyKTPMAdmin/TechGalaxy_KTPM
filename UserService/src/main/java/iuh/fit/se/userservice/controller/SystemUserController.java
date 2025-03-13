package iuh.fit.se.userservice.controller;

import iuh.fit.se.userservice.dto.request.SystemUserRequestDTO;
import iuh.fit.se.userservice.dto.response.DataResponse;
import iuh.fit.se.userservice.dto.response.ResultPaginationDTO;
import iuh.fit.se.userservice.dto.response.SystemUserResponseDTO;
import iuh.fit.se.userservice.entities.SystemUser;
import iuh.fit.se.userservice.entities.enumeration.SystemUserStatus;
import iuh.fit.se.userservice.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system-users")
public class SystemUserController {
    private final SystemUserService systemUserService;

    @Autowired
    public SystemUserController(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @GetMapping
    public ResponseEntity<DataResponse<ResultPaginationDTO>> getAllSystemUsers(Specification<SystemUser> spec, Pageable pageable) {
        ResultPaginationDTO result = systemUserService.fetchAllSystemUser(spec, pageable);
        DataResponse<ResultPaginationDTO> response = DataResponse.<ResultPaginationDTO>builder()
                .status(200)
                .message("Fetch all system users successfully")
                .data(List.of(result))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<DataResponse<SystemUserResponseDTO>> getAllSystemUsers() {
        return ResponseEntity.ok(DataResponse.<SystemUserResponseDTO>builder()
                .status(200)
                .message("Fetch all system users successfully")
                .data(systemUserService.fetchAllSystemUser())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<SystemUserResponseDTO>> getSystemUserById(@PathVariable String id) {
        SystemUserResponseDTO user = systemUserService.fetchUserById(id);
        DataResponse<SystemUserResponseDTO> response = DataResponse.<SystemUserResponseDTO>builder()
                .status(200)
                .message("User fetched successfully")
                .data(List.of(user))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<DataResponse<List<SystemUserResponseDTO>>> getSystemUsersByStatus(@PathVariable SystemUserStatus status) {
        List<SystemUserResponseDTO> users = systemUserService.handleGetSystemUsersByStatus(status);
        DataResponse<List<SystemUserResponseDTO>> response = DataResponse.<List<SystemUserResponseDTO>>builder()
                .status(200)
                .message("Users with status " + status + " fetched successfully")
                .data(List.of(users))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DataResponse<SystemUserResponseDTO>> getSystemUserByEmail(@PathVariable String email) {
        SystemUserResponseDTO user = systemUserService.findSystemUserByEmail(email);
        return ResponseEntity.ok(
                DataResponse.<SystemUserResponseDTO>builder()
                        .status(200)
                        .message("System user fetched successfully")
                        .data(List.of(user))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<DataResponse<SystemUserResponseDTO>> addSystemUser(@RequestBody SystemUserRequestDTO requestDTO) {
        SystemUserResponseDTO response = systemUserService.handleCreateSystemUser(requestDTO);
        return ResponseEntity.ok(
                DataResponse.<SystemUserResponseDTO>builder()
                        .status(201)
                        .message("System user created successfully")
                        .data(List.of(response))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<SystemUserResponseDTO>> updateSystemUser(
            @PathVariable String id, @RequestBody SystemUserRequestDTO requestDTO) {
        requestDTO.setId(id);
        SystemUserResponseDTO response = systemUserService.handleUpdateSystemUser(requestDTO);
        return ResponseEntity.ok(
                DataResponse.<SystemUserResponseDTO>builder()
                        .status(200)
                        .message("System user updated successfully")
                        .data(List.of(response))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<Void>> deleteSystemUser(@PathVariable String id) {
        systemUserService.handleDeleteSystemUser(id);
        return ResponseEntity.ok(
                DataResponse.<Void>builder()
                        .status(200)
                        .message("System user deleted successfully")
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<DataResponse<SystemUserResponseDTO>> updateSystemUserStatus(@RequestBody SystemUserRequestDTO requestDTO) {
        SystemUserResponseDTO response = systemUserService.handleUpdateSystemUser(requestDTO);
        return ResponseEntity.ok(
                DataResponse.<SystemUserResponseDTO>builder()
                        .status(200)
                        .message("System user status updated successfully")
                        .data(List.of(response))
                        .build()
        );
    }

    @DeleteMapping
    public ResponseEntity<DataResponse<Void>> deleteSystemUserByIDs(@RequestBody String ids) {
        systemUserService.handleDeleteSystemUser(ids);
        return ResponseEntity.ok(
                DataResponse.<Void>builder()
                        .status(200)
                        .message("System users deleted successfully")
                        .build()
        );
    }
}
