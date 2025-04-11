package iuh.fit.se.userservice.service.impl;

import iuh.fit.se.userservice.dto.request.CustomerRequest;
import iuh.fit.se.userservice.dto.request.SystemUserRequestDTO;
import iuh.fit.se.userservice.dto.request.UserRegisterRequest;
import iuh.fit.se.userservice.dto.response.*;
import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.entities.Role;
import iuh.fit.se.userservice.entities.enumeration.CustomerStatus;
import iuh.fit.se.userservice.mapper.RoleMapper;
import iuh.fit.se.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class RegistrationServiceImpl {

        private final AccountServiceImpl accountService;
        private final PasswordEncoder passwordEncoder;
        private final RoleService roleService;
        private final RoleMapper roleMapper;
        private final CustomerServiceImpl customerService;
        private final SystemUserServiceImpl systemUserService;
        // private final EmailServiceImpl emailService;

        @Autowired
        public RegistrationServiceImpl(
                        AccountServiceImpl accountService,
                        PasswordEncoder passwordEncoder,
                        RoleService roleService,
                        RoleMapper roleMapper,
                        CustomerServiceImpl customerService,
                        SystemUserServiceImpl systemUserService) {
                this.accountService = accountService;
                this.passwordEncoder = passwordEncoder;
                this.roleService = roleService;
                this.roleMapper = roleMapper;
                this.customerService = customerService;
                this.systemUserService = systemUserService;
        }

        public ResponseEntity<DataResponse<CustommerCreateResponse>> registerCustomer(UserRegisterRequest user) {
                if (user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null
                                || user.getPassword().isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(DataResponse.<CustommerCreateResponse>builder()
                                                        .status(400)
                                                        .message("Email and password are required")
                                                        .build());
                }

                if (accountService.existsByEmail(user.getEmail())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                        .body(DataResponse.<CustommerCreateResponse>builder()
                                                        .status(409)
                                                        .message("Email already exists")
                                                        .build());
                }

                Account account = new Account();
                account.setPassword(passwordEncoder.encode(user.getPassword()));
                account.setEmail(user.getEmail());
                account.setRefreshToken("");

                RoleResponse roleResponse = roleService.findByName("Customer");
                Role role = roleMapper.toEntity(roleResponse);
                if (role == null) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(DataResponse.<CustommerCreateResponse>builder()
                                                        .status(500)
                                                        .message("Role not found")
                                                        .build());
                }
                account.setRoles(Collections.singletonList(role));
                Account newAccount = accountService.createAccount(account);

                if (newAccount.getId() == null) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(DataResponse.<CustommerCreateResponse>builder()
                                                        .status(500)
                                                        .message("Account creation failed")
                                                        .build());
                }

                CustomerRequest customerRequest = new CustomerRequest();
                customerRequest.setName(user.getFullName());
                customerRequest.setAccount(newAccount);
                customerRequest.setUserStatus(CustomerStatus.ACTIVE);
                CustomerResponse customerResponse = this.customerService.save(customerRequest);

                CustommerCreateResponse response = new CustommerCreateResponse();
                response.setName(customerResponse.getName());

                Map<String, Object> variables = Map.of(
                                "name", customerResponse.getName(),
                                "email", newAccount.getEmail(),
                                "registrationDate", LocalDateTime.now().toString());

                // Send email notification
                // emailService.sendSuccessRegistrationEmail(newAccount.getEmail(),
                // "Registration Confirmation", variables);

                return ResponseEntity.ok(DataResponse.<CustommerCreateResponse>builder()
                                .status(200)
                                .message("Account created successfully")
                                .data(Collections.singletonList(response))
                                .build());
        }

        public ResponseEntity<DataResponse<UserRegisterResponse>> createCustomerAccount(UserRegisterRequest user) {
                if (user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null
                                || user.getPassword().isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(DataResponse.<UserRegisterResponse>builder()
                                                        .status(400)
                                                        .message("Email and password are required")
                                                        .build());
                }

                if (accountService.existsByEmail(user.getEmail())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                        .body(DataResponse.<UserRegisterResponse>builder()
                                                        .status(409)
                                                        .message("Email already exists")
                                                        .build());
                }

                Account account = new Account();
                account.setPassword(passwordEncoder.encode(user.getPassword()));
                account.setEmail(user.getEmail());
                account.setRefreshToken("");

                RoleResponse roleResponse = roleService.findByName("Customer");
                Role role = roleMapper.toEntity(roleResponse);
                if (role == null) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(DataResponse.<UserRegisterResponse>builder()
                                                        .status(500)
                                                        .message("Role not found")
                                                        .build());
                }

                account.setRoles(Collections.singletonList(role));
                Account newAccount = accountService.createAccount(account);

                if (newAccount.getId() == null) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(DataResponse.<UserRegisterResponse>builder()
                                                        .status(500)
                                                        .message("Account creation failed")
                                                        .build());
                }

                UserRegisterResponse response = new UserRegisterResponse();
                response.setEmail(newAccount.getEmail());
                response.setId(newAccount.getId());

                return ResponseEntity.ok(DataResponse.<UserRegisterResponse>builder()
                                .status(200)
                                .message("Account created successfully")
                                .data(Collections.singletonList(response))
                                .build());
        }

        public ResponseEntity<DataResponse<UserRegisterResponse>> createSystemUserAccount(UserRegisterRequest user) {
                if (user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null
                                || user.getPassword().isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(DataResponse.<UserRegisterResponse>builder()
                                                        .status(400)
                                                        .message("Email and password are required")
                                                        .build());
                }

                if (accountService.existsByEmail(user.getEmail())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                        .body(DataResponse.<UserRegisterResponse>builder()
                                                        .status(409)
                                                        .message("Email already exists")
                                                        .build());
                }

                Account account = new Account();
                account.setPassword(passwordEncoder.encode(user.getPassword()));
                account.setEmail(user.getEmail());
                account.setRefreshToken("");

                RoleResponse roleResponse = roleService.findByName("Employee");
                Role role = roleMapper.toEntity(roleResponse);
                if (role == null) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(DataResponse.<UserRegisterResponse>builder()
                                                        .status(500)
                                                        .message("Role not found")
                                                        .build());
                }

                account.setRoles(Collections.singletonList(role));
                Account newAccount = accountService.createAccount(account);

                if (newAccount.getId() == null) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(DataResponse.<UserRegisterResponse>builder()
                                                        .status(500)
                                                        .message("Account creation failed")
                                                        .build());
                }

                UserRegisterResponse response = new UserRegisterResponse();
                response.setEmail(newAccount.getEmail());
                response.setId(newAccount.getId());

                return ResponseEntity.ok(DataResponse.<UserRegisterResponse>builder()
                                .status(200)
                                .message("Account created successfully")
                                .data(Collections.singletonList(response))
                                .build());
        }

        public ResponseEntity<DataResponse<SystemUserResponseDTO>> createSystemUser(SystemUserRequestDTO user) {
                if (user.getAccount().getEmail() == null || user.getAccount().getEmail().isEmpty() ||
                                user.getAccount().getPassword() == null || user.getAccount().getPassword().isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(DataResponse.<SystemUserResponseDTO>builder()
                                                        .status(400)
                                                        .message("Email and password are required")
                                                        .build());
                }

                if (accountService.existsByEmail(user.getAccount().getEmail())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                        .body(DataResponse.<SystemUserResponseDTO>builder()
                                                        .status(409)
                                                        .message("Email already exists")
                                                        .build());
                }

                Account account = new Account();
                account.setPassword(passwordEncoder.encode(user.getAccount().getPassword()));
                account.setEmail(user.getAccount().getEmail());
                account.setRefreshToken("");

                List<Role> roles = user.getAccount().getRoles();

                if (roles == null || roles.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(DataResponse.<SystemUserResponseDTO>builder()
                                                        .status(500)
                                                        .message("Role not found")
                                                        .build());
                }

                account.setRoles(roles);
                Account newAccount = accountService.createAccount(account);

                SystemUserResponseDTO.AccountResponse response = new SystemUserResponseDTO.AccountResponse();
                response.setEmail(newAccount.getEmail());
                SystemUserResponseDTO systemUserResponseDTO = systemUserService.handleCreateSystemUser(user);

                return ResponseEntity.ok(DataResponse.<SystemUserResponseDTO>builder()
                                .status(200)
                                .message("Account created successfully")
                                .data(Collections.singletonList(systemUserResponseDTO))
                                .build());
        }
}