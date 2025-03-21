package iuh.fit.se.userservice.service.impl;

import iuh.fit.se.userservice.dto.request.RoleRequest;
import iuh.fit.se.userservice.dto.response.ResultPaginationDTO;
import iuh.fit.se.userservice.dto.response.RoleResponse;
import iuh.fit.se.userservice.entities.Permission;
import iuh.fit.se.userservice.entities.Role;
import iuh.fit.se.userservice.mapper.RoleMapper;
import iuh.fit.se.userservice.repository.PermissionRepository;
import iuh.fit.se.userservice.repository.RoleRepository;
import iuh.fit.se.userservice.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository,
            RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public RoleResponse findByName(String name) {
        Role role = roleRepository.findByName(name);
        return roleMapper.toResponse(role);
    }

    @Override
    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toEntity(request);

        if (request.getPermissionIds() != null) {
            List<Permission> permissions = permissionRepository.findByIdIn(request.getPermissionIds());
            role.setPermissions(permissions);
        }

        Role savedRole = roleRepository.save(role);
        return roleMapper.toResponse(savedRole);
    }

    @Override
    public RoleResponse fetchById(String id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.map(roleMapper::toResponse).orElse(null);
    }

    @Override
    public RoleResponse update(RoleRequest request) {
        Role existingRole = roleRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (request.getPermissionIds() != null) {
            List<Permission> permissions = permissionRepository.findByIdIn(request.getPermissionIds());
            existingRole.setPermissions(permissions);
        }

        existingRole.setName(request.getName());
        existingRole.setDescription(request.getDescription());
        existingRole.setActive(request.isActive());

        Role updatedRole = roleRepository.save(existingRole);
        return roleMapper.toResponse(updatedRole);
    }

    @Override
    public void delete(String id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found");
        }
        if (roleRepository.existsById(id)) {
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Role not found"));
            role.getPermissions().clear();
            roleRepository.deleteById(id);
        }
        roleRepository.deleteById(id);
    }

    @Override
    public ResultPaginationDTO getRoles(Specification<Role> spec, Pageable pageable) {
        Page<Role> pageRoles = roleRepository.findAll(spec, pageable);
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageRoles.getTotalPages());
        meta.setTotal(pageRoles.getTotalElements());

        ResultPaginationDTO result = new ResultPaginationDTO();
        result.setMeta(meta);
        result.setResult(pageRoles.getContent().stream().map(roleMapper::toResponse).collect(Collectors.toList()));

        return result;
    }

    @Override
    public List<Role> findByNameIn(List<String> names) {
        List<Role> roles = roleRepository.findByNameIn(names);
        return roles;
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleResponse> fechByEmail(String email) {
        System.out.println("Email: " + email);
        return roleRepository.findRolesByAccountEmail(email).stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());
    }
}
