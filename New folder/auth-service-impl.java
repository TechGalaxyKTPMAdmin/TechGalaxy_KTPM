package com.techgalaxy.authservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techgalaxy.authservice.dto.request.LoginRequest;
import com.techgalaxy.authservice.dto.request.RefreshTokenRequest;
import com.techgalaxy.authservice.dto.request.RegisterRequest;
import com.techgalaxy.authservice.dto.response.AuthResponse;
import com.techgalaxy.authservice.dto.response.LoginResponse;
import com.techgalaxy.authservice.dto.response.RegisterResponse;
import com.techgalaxy.authservice.dto.response.ValidateTokenResponse;
import com.techgalaxy.authservice.entity.Account;
import com.techgalaxy.authservice.entity.BlacklistedToken;
import com.techgalaxy.authservice.entity.Permission;
import com.techgalaxy.authservice.entity.Role;
import com.techgalaxy.authservice.exception.AppException;
import com.techgalaxy.authservice.exception.ErrorCode;
import com.techgalaxy.authservice.repository.AccountRepository;
import com.techgalaxy.authservice.repository.BlacklistedTokenRepository;
import com.techgalaxy.authservice.repository.RoleRepository;
import com.techgalaxy.authservice.service.AuthService;
import com.techgalaxy.authservice.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        // Check if email exists
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        // Create new account
        Account account = Account.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .build();

        // Assign default role
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        account.setRoles(Collections.singletonList(userRole));

        // Save account
        accountRepository.save(account);

        return RegisterResponse.builder()
                .message("Registration successful")
                .build();
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        // Find account by email
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        // Create login response
        LoginResponse response = new LoginResponse();
        LoginResponse.AccountDto accountDto = new LoginResponse.AccountDto();
        accountDto.setId(account.getId());
        accountDto.setEmail(account.getEmail());
        accountDto.setFullName(account.getFullName());
        
        // Set roles and gather permissions
        List<String> roleNames = new ArrayList<>();
        List<String> permissions = new ArrayList<>();
        
        for (Role role : account.getRoles()) {
            roleNames.add(role.getName());
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getApiPath() + ":" + permission.getMethod());
            }
        }
        
        accountDto.setRoles(roleNames);
        accountDto.setPermissions(permissions);
        response.setAccount(accountDto);

        // Generate tokens
        String accessToken = securityUtil.createAccessToken(account.getEmail(), response);
        String refreshToken = securityUtil.createRefreshToken(account.getEmail(), response);
        
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        
        return response;
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        // Validate refresh token
        Jwt jwt;
        try {
            jwt = securityUtil.checkValidRefreshToken(request.getRefreshToken());
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // Check if token is blacklisted
        if (blacklistedTokenRepository.findByToken(request.getRefreshToken()).isPresent()) {
            throw new AppException(ErrorCode.TOKEN_BLACKLISTED);
        }

        // Get user email from token
        String email = jwt.getSubject();
        
        // Find account
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        // Create response for token generation
        LoginResponse tokenResponse = new LoginResponse();
        LoginResponse.AccountDto accountDto = new LoginResponse.AccountDto();
        accountDto.setId(account.getId());
        accountDto.setEmail(account.getEmail());
        
        // Set roles and gather permissions
        List<String> roleNames = new ArrayList<>();
        List<String> permissions = new ArrayList<>();
        
        for (Role role : account.getRoles()) {
            roleNames.add(role.getName());
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getApiPath() + ":" + permission.getMethod());
            }
        }
        
        accountDto.setRoles(roleNames);
        accountDto.setPermissions(permissions);
        tokenResponse.setAccount(accountDto);

        // Generate new access token
        String accessToken = securityUtil.createAccessToken(email, tokenResponse);
        
        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    @Transactional
    public void logout(String token) {
        // Add token to blacklist
        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(token)
                .createdAt(new Date())
                .build();
        
        blacklistedTokenRepository.save(blacklistedToken);
    }

    @Override
    public ValidateTokenResponse validateToken(String token) {
        try {
            // Check if token is blacklisted
            if (blacklistedTokenRepository.findByToken(token).isPresent()) {
                return ValidateTokenResponse.builder().valid(false).build();
            }
            
            // Validate token
            Map<String, Object> claims = securityUtil.validateTokenAndGetClaims(token);
            
            return ValidateTokenResponse.builder()
                    .valid(true)
                    .userId(getUserIdFromClaims(claims))
                    .email(claims.get("sub").toString())
                    .permissions(getPermissionsFromClaims(claims))
                    .build();
        } catch (Exception e) {
            log.error("Token validation error", e);
            return ValidateTokenResponse.builder().valid(false).build();
        }
    }

    @Override
    public Object getUserPermissions(String token) {
        try {
            // Validate token
            Map<String, Object> claims = securityUtil.validateTokenAndGetClaims(token);
            
            return Map.of(
                "userId", getUserIdFromClaims(claims),
                "email", claims.get("sub").toString(),
                "permissions", getPermissionsFromClaims(claims)
            );
        } catch (Exception e) {
            log.error("Error getting user permissions", e);
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
    
    @SuppressWarnings("unchecked")
    private String getUserIdFromClaims(Map<String, Object> claims) {
        try {
            Map<String, Object> user = (Map<String, Object>) claims.get("user");
            return user.get("id").toString();
        } catch (Exception e) {
            log.error("Error getting user ID from claims", e);
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<String> getPermissionsFromClaims(Map<String, Object> claims) {
        try {
            return (List<String>) claims.get("permission");
        } catch (Exception e) {
            log.error("Error getting permissions from claims", e);
            return Collections.emptyList();
        }
    }
}
