package iuh.fit.se.userservice.controller;

import com.turkraft.springfilter.boot.Filter;
import iuh.fit.se.userservice.dto.request.PermissionRequest;
import iuh.fit.se.userservice.dto.response.DataResponse;
import iuh.fit.se.userservice.dto.response.PermissionResponse;
import iuh.fit.se.userservice.dto.response.ResultPaginationDTO;
import iuh.fit.se.userservice.entities.Permission;
import iuh.fit.se.userservice.service.impl.PermissionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionServiceImpl permissionService;

    @Autowired
    public PermissionController(PermissionServiceImpl permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public ResponseEntity<DataResponse<PermissionResponse>> create(@Valid @RequestBody PermissionRequest request) {
        // Check if permission already exists
        if (this.permissionService.isPermissionExist(request)) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.<PermissionResponse>builder().message("Permission đã tồn tại.").build());
        }

        PermissionResponse response = this.permissionService.create(request);
        return ResponseEntity.status(201).body(DataResponse.<PermissionResponse>builder().data(List.of(response)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<PermissionResponse>> update(
            @PathVariable("id") String id,
            @Valid @RequestBody PermissionRequest request) {
        // Set ID for the request
        request.setId(id);

        // Check if permission exists by ID
        if (this.permissionService.fetchById(id) == null) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.<PermissionResponse>builder().message("Permission với id = " + id + " không tồn tại.").build());
        }

        // Check if permission exists by module, apiPath, and method
        if (this.permissionService.isPermissionExist(request) && !this.permissionService.isSameName(request)) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.<PermissionResponse>builder().message("Permission đã tồn tại.").build());
        }

        PermissionResponse response = this.permissionService.update(request);
        return ResponseEntity.ok(DataResponse.<PermissionResponse>builder().data(List.of(response)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<Void>> delete(@PathVariable("id") String id) {
        // Check if permission exists by ID
        if (this.permissionService.fetchById(id) == null) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.<Void>builder().message("Permission với id = " + id + " không tồn tại.").build());
        }

        this.permissionService.delete(id);
        return ResponseEntity.ok().body(DataResponse.<Void>builder().message("Xóa permission thành công.").build());
    }

    @GetMapping
    public ResponseEntity<DataResponse<ResultPaginationDTO>> getPermissions(
            @Filter Specification<Permission> spec, Pageable pageable) {
        ResultPaginationDTO result = this.permissionService.getPermissions(spec, pageable);
        return ResponseEntity.ok().body(DataResponse.<ResultPaginationDTO>builder().data(List.of(result)).build());
    }
}