package iuh.fit.se.userservice.service.impl;

import iuh.fit.se.userservice.dto.request.PermissionRequest;
import iuh.fit.se.userservice.dto.response.PermissionResponse;
import iuh.fit.se.userservice.dto.response.ResultPaginationDTO;
import iuh.fit.se.userservice.entities.Permission;
import iuh.fit.se.userservice.mapper.PermissionMapper;
import iuh.fit.se.userservice.repository.PermissionRepository;
import iuh.fit.se.userservice.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public boolean isPermissionExist(PermissionRequest request) {
        return permissionRepository.existsByModuleAndApiPathAndMethod(
                request.getModule(),
                request.getApiPath(),
                request.getMethod()
        );
    }

    @Override
    public PermissionResponse fetchById(String id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        return permissionMapper.toResponse(permission);
    }

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toEntity(request);
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toResponse(savedPermission);
    }

    @Override
    public PermissionResponse update(PermissionRequest request) {
        Permission existingPermission = permissionRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        permissionMapper.updateEntityFromRequest(request, existingPermission);
        Permission updatedPermission = permissionRepository.save(existingPermission);
        return permissionMapper.toResponse(updatedPermission);
    }

    @Override
    public void delete(String id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        permission.getRoles().forEach(role -> role.getPermissions().remove(permission));
        permissionRepository.delete(permission);
    }

    @Override
    public ResultPaginationDTO getPermissions(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> pPermissions = this.permissionRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pPermissions.getTotalPages());
        mt.setTotal(pPermissions.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pPermissions.getContent());
        return rs;
    }

    @Override
    public boolean isSameName(PermissionRequest request) {
        Permission permission = permissionRepository.findByName(request.getName());
        return permission.getId().equals(request.getId());
    }
}

