package iuh.fit.se.userservice.controller;

import com.turkraft.springfilter.boot.Filter;
import iuh.fit.se.userservice.dto.request.RoleRequest;
import iuh.fit.se.userservice.dto.response.DataResponse;
import iuh.fit.se.userservice.dto.response.ResultPaginationDTO;
import iuh.fit.se.userservice.dto.response.RoleResponse;
import iuh.fit.se.userservice.entities.Role;
import iuh.fit.se.userservice.service.impl.RoleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleServiceImpl roleService;

    @Autowired
    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<DataResponse<RoleResponse>> create(@Valid @RequestBody RoleRequest request) {
        if (this.roleService.existsByName(request.getName())) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.<RoleResponse>builder().message("Role đã tồn tại.").build());
        }

        RoleResponse roleResponse = this.roleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponse.<RoleResponse>builder().data(List.of(roleResponse)).build());
    }

    @PutMapping
    public ResponseEntity<DataResponse<RoleResponse>> update(@Valid @RequestBody RoleRequest request) {
        if (this.roleService.fetchById(request.getId()) == null) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.<RoleResponse>builder()
                            .message("Role với id = " + request.getId() + " không tồn tại.").build());
        }

        RoleResponse roleResponse = this.roleService.update(request);
        return ResponseEntity.ok(DataResponse.<RoleResponse>builder().data(List.of(roleResponse)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<Void>> delete(@PathVariable("id") String id) {
        if (this.roleService.fetchById(id) == null) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.<Void>builder().message("Role với id = " + id + " không tồn tại.").build());
        }

        this.roleService.delete(id);
        return ResponseEntity.ok().body(DataResponse.<Void>builder().message("Xóa thành công").build());
    }

    @GetMapping
    public ResponseEntity<DataResponse<ResultPaginationDTO>> getRoles(
            @Filter Specification<Role> spec, Pageable pageable) {

        ResultPaginationDTO resultPagination = this.roleService.getRoles(spec, pageable);
        return ResponseEntity.ok(DataResponse.<ResultPaginationDTO>builder().data(List.of(resultPagination)).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<RoleResponse>> getById(@PathVariable("id") String id) {
        RoleResponse roleResponse = this.roleService.fetchById(id);

        if (roleResponse == null) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.<RoleResponse>builder().message("Role với id = " + id + " không tồn tại.")
                            .build());
        }

        return ResponseEntity.ok(DataResponse.<RoleResponse>builder().data(List.of(roleResponse)).build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DataResponse<RoleResponse>> getByEmail(@PathVariable("email") String email) {
        List<RoleResponse> roleResponse = this.roleService.fechByEmail(email);

        if (roleResponse == null) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.<RoleResponse>builder().message("Role với email = " + email + " không tồn tại.")
                            .build());
        }

        return ResponseEntity.ok(DataResponse.<RoleResponse>builder().data(roleResponse).build());
    }

    @GetMapping("/all")
    public ResponseEntity<DataResponse<RoleResponse>> getAll() {
        return ResponseEntity.ok(DataResponse.<RoleResponse>builder().data(this.roleService.findAll()).build());
    }
}
