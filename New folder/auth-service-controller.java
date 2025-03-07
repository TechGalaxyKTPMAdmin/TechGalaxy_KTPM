package com.techgalaxy.authservice.controller;

import com.techgalaxy.authservice.dto.request.LoginRequest;
import com.techgalaxy.authservice.dto.request.RefreshTokenRequest;
import com.techgalaxy.authservice.dto.request.RegisterRequest;
import com.techgalaxy.authservice.dto.response.AuthResponse;
import com.techgalaxy.authservice.dto.response.LoginResponse;
import com.techgalaxy.authservice.dto.response.RegisterResponse;
import com.techgalaxy.authservice.dto.response.ValidateTokenResponse;
import com.techgalaxy.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.replace("Bearer ", ""));
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/validate-token")
    public ResponseEntity<ValidateTokenResponse> validateToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.validateToken(token.replace("Bearer ", "")));
    }
    
    @GetMapping("/user-permissions")
    public ResponseEntity<Object> getUserPermissions(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.getUserPermissions(token.replace("Bearer ", "")));
    }
}
