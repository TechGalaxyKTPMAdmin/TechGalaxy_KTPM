package iuh.fit.se.userservice.controller;

import iuh.fit.se.userservice.dto.request.LoginRequest;
import iuh.fit.se.userservice.dto.request.SystemUserRequestDTO;
import iuh.fit.se.userservice.dto.request.UserRegisterRequest;
import iuh.fit.se.userservice.dto.response.*;
import iuh.fit.se.userservice.mapper.AccountMapper;
import iuh.fit.se.userservice.service.impl.AccountServiceImpl;
import iuh.fit.se.userservice.service.impl.AuthenticationServiceImpl;
import iuh.fit.se.userservice.service.impl.RegistrationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class AuthController {
    private final AccountServiceImpl accountService;
    private final AuthenticationServiceImpl authService;
    private final RegistrationServiceImpl registrationService;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;


    @Autowired
    public AuthController(
            AccountServiceImpl accountService,
            AuthenticationServiceImpl authService,
            RegistrationServiceImpl registrationService,
            PasswordEncoder passwordEncoder,
            AccountMapper accountMapper) {
        this.accountService = accountService;
        this.authService = authService;
        this.registrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
        this.accountMapper = accountMapper;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<DataResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginDto) {
        return authService.login(loginDto);
    }

    @GetMapping("/auth/account")
    public ResponseEntity<DataResponse<LoginResponse.AccountGetAccount>> getAccount() {
        return authService.getAccount();
    }

    @PostMapping("/auth/register")
    public ResponseEntity<DataResponse<CustommerCreateResponse>> register(@Valid @RequestBody UserRegisterRequest user) {
        return registrationService.registerCustomer(user);
    }

    @PostMapping("/auth/create-customer-account")
    public ResponseEntity<DataResponse<UserRegisterResponse>> createCustomerAccount(@Valid @RequestBody UserRegisterRequest user) {
        return registrationService.createCustomerAccount(user);
    }

    @PostMapping("/auth/create-system-user-account")
    public ResponseEntity<DataResponse<UserRegisterResponse>> createSystemUserAccount(@Valid @RequestBody UserRegisterRequest user) {
        return registrationService.createSystemUserAccount(user);
    }

    @PostMapping("/auth/create-system-user")
    public ResponseEntity<DataResponse<SystemUserResponseDTO>> register(@Valid @RequestBody SystemUserRequestDTO user) {
        return registrationService.createSystemUser(user);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<DataResponse<String>> logout(HttpServletRequest request) {
        return authService.logout();
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<DataResponse<LoginResponse>> refreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "abc") String refresh_token) {
        return authService.refreshToken(refresh_token);
    }


    @PostMapping("/auth/validate-token")
    public ResponseEntity<DataResponse<ValidateTokenResponse>> validateToken(
            @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        String token = authHeader.substring(7);
        return authService.validateToken(token);

    }
}
