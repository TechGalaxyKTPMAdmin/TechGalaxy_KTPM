package iuh.fit.se.userservice.controller;

import iuh.fit.se.userservice.dto.request.LoginRequest;
import iuh.fit.se.userservice.dto.request.SystemUserRequestDTO;
import iuh.fit.se.userservice.dto.request.UserRegisterRequest;
import iuh.fit.se.userservice.dto.response.*;
import iuh.fit.se.userservice.mapper.AccountMapper;
import iuh.fit.se.userservice.service.AuthenticationService;
import iuh.fit.se.userservice.service.impl.AccountServiceImpl;
import iuh.fit.se.userservice.service.impl.AuthenticationServiceImpl;
import iuh.fit.se.userservice.service.impl.RegistrationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
@Slf4j
@RequestMapping("/api/accounts/auth")
public class AuthController {
    private final AuthenticationService authService;
    private final RegistrationServiceImpl registrationService;

    @Autowired
    public AuthController(
            AccountServiceImpl accountService,
            AuthenticationService authService,
            RegistrationServiceImpl registrationService,
            PasswordEncoder passwordEncoder,
            AccountMapper accountMapper) {
        this.authService = authService;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<DataResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginDto) {
        System.out.println("Login request: " + loginDto);
        return authService.login(loginDto);
    }

    @GetMapping("/account")
    public ResponseEntity<DataResponse<LoginResponse.AccountGetAccount>> getAccount() {
        return authService.getAccount();
    }

    @PostMapping("/register")
    public ResponseEntity<DataResponse<CustommerCreateResponse>> register(
            @Valid @RequestBody UserRegisterRequest user) {
        return registrationService.registerCustomer(user);
    }

    @PostMapping("/create-customer-account")
    public ResponseEntity<DataResponse<UserRegisterResponse>> createCustomerAccount(
            @Valid @RequestBody UserRegisterRequest user) {
        return registrationService.createCustomerAccount(user);
    }

    @PostMapping("/create-system-user-account")
    public ResponseEntity<DataResponse<UserRegisterResponse>> createSystemUserAccount(
            @Valid @RequestBody UserRegisterRequest user) {
        return registrationService.createSystemUserAccount(user);
    }

    @PostMapping("/create-system-user")
    public ResponseEntity<DataResponse<SystemUserResponseDTO>> register(@Valid @RequestBody SystemUserRequestDTO user) {
        return registrationService.createSystemUser(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<DataResponse<String>> logout(HttpServletRequest request) {
        return authService.logout(request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<DataResponse<LoginResponse>> refreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "abc") String refresh_token) {
        return authService.refreshToken(refresh_token);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<DataResponse<ValidateTokenResponse>> validateToken(
            @RequestHeader("Authorization") String authHeader,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Invalid Authorization header [{}]", requestId);
                return ResponseEntity.badRequest().build();
            }
            String token = authHeader.substring(7);
            log.info("Received token [{}]: {}", requestId, token);
            ResponseEntity<DataResponse<ValidateTokenResponse>> response = authService.validateToken(token);
            log.info("Returning response [{}]: {}", requestId, response.getBody());
            return response;
        } catch (Exception e) {
            log.error("Error validating token [{}]: {}", requestId, e.getMessage());
            return ResponseEntity.status(401).body(new DataResponse<>(401, "Unauthorized", null));
        }
    }

    public ResponseEntity<String> rateLimiterFallback(Exception ex) {
        return ResponseEntity.status(429).body("Too many requests - please try again later.");
    }

    // @PostMapping("/validate-token")
    // public ResponseEntity<ValidateTokenResponse> validateToken(
    // @RequestHeader("Authorization") String authHeader,
    // @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
    // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    // log.warn("Invalid Authorization header [{}]", requestId);
    // return ResponseEntity.badRequest().build();
    // }
    // String token = authHeader.substring(7);
    // log.info("Received token [{}]: {}", requestId, token);
    // ResponseEntity<ValidateTokenResponse> response =
    // authService.validateTokenRaw(token);
    // log.info("Returning response [{}]: {}", requestId, response.getBody());
    // return response;
    // }

}
