package iuh.fit.se.userservice.service.impl;

import iuh.fit.se.userservice.dto.request.SystemUserRequestDTO;
import iuh.fit.se.userservice.dto.response.ResultPaginationDTO;
import iuh.fit.se.userservice.dto.response.SystemUserResponseDTO;
import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.entities.SystemUser;
import iuh.fit.se.userservice.entities.enumeration.SystemUserStatus;
import iuh.fit.se.userservice.exception.AppException;
import iuh.fit.se.userservice.exception.ErrorCode;
import iuh.fit.se.userservice.mapper.SystemUserMapper;
import iuh.fit.se.userservice.repository.AccountRepository;
import iuh.fit.se.userservice.repository.SystemUserRepository;
import iuh.fit.se.userservice.service.SystemUserService;
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
public class SystemUserServiceImpl implements SystemUserService {
    private final SystemUserRepository systemUserRepository;
    private final AccountRepository accountRepository;


    @Autowired
    public SystemUserServiceImpl(SystemUserRepository systemUserRepository, AccountRepository accountRepository) {
        this.systemUserRepository = systemUserRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public SystemUserResponseDTO handleCreateSystemUser(SystemUserRequestDTO userRequestDTO) {
        // Map DTO to entity
        SystemUserRequestDTO.AccountRequest accountRequest = userRequestDTO.getAccount();
        Account account = accountRepository.findByEmail(accountRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        SystemUser systemUser = SystemUserMapper.INSTANCE.toEntity(userRequestDTO);
        systemUser.setAccount(account);
        SystemUser savedSystemUser = systemUserRepository.save(systemUser);
        return SystemUserMapper.INSTANCE.toResponseDTO(savedSystemUser);
    }

    @Override
    public void handleDeleteSystemUser(String id) {
        Optional<SystemUser> userOpt = systemUserRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy người dùng với id: " + id);
        }
        systemUserRepository.deleteById(id);
    }

    @Override
    public SystemUserResponseDTO fetchUserById(String id) {
        SystemUser systemUser = systemUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SystemUser not found"));
        return SystemUserMapper.INSTANCE.toResponseDTO(systemUser);
    }

    @Override
    public List<SystemUserResponseDTO> fetchAllSystemUser() {
        List<SystemUser> users = systemUserRepository.findAll();
        return users.stream()
                .map(SystemUserMapper.INSTANCE::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResultPaginationDTO fetchAllSystemUser(Specification<SystemUser> spec, Pageable pageable) {
        Page<SystemUser> page = systemUserRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(page.getTotalPages());
        mt.setTotal(page.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(page.getContent());
        return rs;

    }

    @Override
    public SystemUserResponseDTO handleUpdateSystemUser(SystemUserRequestDTO reqUser) {
        SystemUser existingUser = systemUserRepository.findById(reqUser.getId())
                .orElseThrow(() -> new AppException(ErrorCode.SYSTEM_USER_NOT_FOUND));
        if (reqUser.getName() != null)
            existingUser.setName(reqUser.getName());
        if (reqUser.getPhone() != null)
            existingUser.setPhone(reqUser.getPhone());
        if (reqUser.getAddress() != null)
            existingUser.setAddress(reqUser.getAddress());
        if (reqUser.getSystemUserStatus() != null)
            existingUser.setSystemUserStatus(reqUser.getSystemUserStatus());
        if (reqUser.getLevel() != null)
            existingUser.setLevel(reqUser.getLevel());
        if (reqUser.getGender() != null)
            existingUser.setGender(reqUser.getGender());
        if (reqUser.getAvatar() != null)
            existingUser.setAvatar(reqUser.getAvatar());

        SystemUser updatedUser = systemUserRepository.save(existingUser);

        return SystemUserMapper.INSTANCE.toResponseDTO(updatedUser);
    }


    @Override
    public boolean isEmailExist(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public List<SystemUserResponseDTO> handleGetSystemUsersByStatus(SystemUserStatus status) {
        List<SystemUser> users = systemUserRepository.findBySystemUserStatus(status);
        return users.stream().map(SystemUserMapper.INSTANCE::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public SystemUserResponseDTO findSystemUserByEmail(String email) {
        return SystemUserMapper.INSTANCE.toResponseDTO(systemUserRepository.findSystemUserByEmail(email));
    }
}
