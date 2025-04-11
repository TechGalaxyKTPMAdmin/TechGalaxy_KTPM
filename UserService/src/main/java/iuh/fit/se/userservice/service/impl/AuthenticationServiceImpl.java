package iuh.fit.se.userservice.service.impl;

import iuh.fit.se.userservice.dto.request.LoginRequest;
import iuh.fit.se.userservice.dto.response.*;
import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.entities.Permission;
import iuh.fit.se.userservice.entities.Role;
import iuh.fit.se.userservice.entities.enumeration.CustomerStatus;
import iuh.fit.se.userservice.entities.enumeration.SystemUserStatus;
import iuh.fit.se.userservice.exception.AppException;
import iuh.fit.se.userservice.exception.ErrorCode;
import iuh.fit.se.userservice.service.AuthenticationService;
import iuh.fit.se.userservice.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

        private final AuthenticationManagerBuilder authenticationManagerBuilder;
        private final AccountServiceImpl accountService;
        private final SecurityUtil securityUtil;

        private final JwtDecoder jwtDecoder;

        @Value("${jwt.refresh-token-validity-in-seconds}")
        private long refreshTokenExpiration;

        @Autowired
        public AuthenticationServiceImpl(
                        AuthenticationManagerBuilder authenticationManagerBuilder,
                        AccountServiceImpl accountService,
                        SecurityUtil securityUtil,
                        JwtDecoder jwtDecoder) {
                this.authenticationManagerBuilder = authenticationManagerBuilder;
                this.accountService = accountService;
                this.securityUtil = securityUtil;
                this.jwtDecoder = jwtDecoder;
        }

        @Override
        public ResponseEntity<DataResponse<LoginResponse>> login(LoginRequest loginDto) {
                log.info("Login with username: {}", loginDto.getUsername());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                loginDto.getUsername(), loginDto.getPassword());
                try {
                        Authentication authentication = authenticationManagerBuilder.getObject()
                                        .authenticate(authenticationToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        LoginResponse res = new LoginResponse();
                        Account currentUserDB = accountService.getAccountByEmail(loginDto.getUsername()).orElse(null);

                        if (currentUserDB == null) {
                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                .body(DataResponse.<LoginResponse>builder()
                                                                .status(401)
                                                                .message("Invalid username or password")
                                                                .build());
                        }

                        if (isAccountInactive(currentUserDB)) {
                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                .body(DataResponse.<LoginResponse>builder()
                                                                .status(401)
                                                                .message("Account is inactive")
                                                                .build());
                        }

                        res.setAccount(new LoginResponse.AccountLogin(
                                        currentUserDB.getId(),
                                        currentUserDB.getEmail()));

                        String accessToken = securityUtil.createAccessToken(authentication.getName(), res);
                        res.setAccessToken(accessToken);

                        String refreshToken = securityUtil.createRefreshToken(loginDto.getUsername(), res);
                        this.accountService.updateToken(refreshToken, loginDto.getUsername());

                        ResponseCookie resCookie = ResponseCookie
                                        .from("refresh_token", refreshToken)
                                        .httpOnly(true)
                                        .secure(true)
                                        .path("/")
                                        .maxAge(refreshTokenExpiration)
                                        .build();

                        return ResponseEntity.ok()
                                        .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                                        .body(DataResponse.<LoginResponse>builder()
                                                        .status(200)
                                                        .message("Login successful")
                                                        .data(Collections.singletonList(res))
                                                        .build());
                } catch (AuthenticationException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(DataResponse.<LoginResponse>builder()
                                                        .status(401)
                                                        .message("Invalid username or password")
                                                        .build());
                }
        }

        private boolean isAccountInactive(Account account) {
                if (account.getSystemUsers() != null) {
                        SystemUserStatus status = account.getSystemUsers().getSystemUserStatus();
                        return status == SystemUserStatus.INACTIVE || status == SystemUserStatus.SUSPENDED;
                } else if (account.getCustomers() != null) {
                        CustomerStatus status = account.getCustomers().getUserStatus();
                        return status == CustomerStatus.INACTIVE || status == CustomerStatus.SUSPENDED;
                }
                return true;
        }

        @Override
        public ResponseEntity<DataResponse<LoginResponse.AccountGetAccount>> getAccount() {
                String email = SecurityUtil.getCurrentUserLogin().orElse(null);
                if (email == null) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(DataResponse.<LoginResponse.AccountGetAccount>builder()
                                                        .status(401)
                                                        .message("No user logged in")
                                                        .build());
                }

                Account currentAccount = accountService.getAccountByEmail(email).orElse(null);
                if (currentAccount == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(DataResponse.<LoginResponse.AccountGetAccount>builder()
                                                        .status(404)
                                                        .message("User not found")
                                                        .build());
                }

                LoginResponse.AccountGetAccount accountResponse = new LoginResponse.AccountGetAccount();
                accountResponse.setAccount(new LoginResponse.AccountLogin(
                                currentAccount.getId(),
                                currentAccount.getEmail()));

                return ResponseEntity.ok(DataResponse.<LoginResponse.AccountGetAccount>builder()
                                .status(200)
                                .message("Account retrieved successfully")
                                .data(Collections.singletonList(accountResponse))
                                .build());
        }

        @Override
        public ResponseEntity<DataResponse<String>> logout() {
                String email = SecurityUtil.getCurrentUserLogin().orElse("");

                if (email.isEmpty()) {
                        throw new AppException(ErrorCode.NO_LOGIN);
                }

                // Update token in account
                this.accountService.updateToken("", email);

                // Clear refresh token cookie
                ResponseCookie resCookie = ResponseCookie
                                .from("refresh_token", "")
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(0)
                                .build();

                // Clear security context
                SecurityContextHolder.clearContext();

                // Return response
                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                                .body(DataResponse.<String>builder()
                                                .status(200)
                                                .message("Logout successful")
                                                .data(Collections.singletonList("Logout successful"))
                                                .build());
        }

        @Override
        public ResponseEntity<DataResponse<LoginResponse>> refreshToken(String refresh_token) {
                if (refresh_token == null || refresh_token.isEmpty() || refresh_token.equals("abc")) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(DataResponse.<LoginResponse>builder()
                                                        .status(401)
                                                        .message("No refresh token found")
                                                        .build());
                }

                Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refresh_token);
                String email = decodedToken.getSubject();

                if (email == null) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(DataResponse.<LoginResponse>builder()
                                                        .status(401)
                                                        .message("Invalid refresh token")
                                                        .build());
                }

                Account currentAccount = this.accountService.getAcountByRefreshTokenAndEmail(refresh_token, email);
                if (currentAccount == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(DataResponse.<LoginResponse>builder()
                                                        .status(404)
                                                        .message("User not found")
                                                        .build());
                }

                LoginResponse res = new LoginResponse();
                res.setAccount(new LoginResponse.AccountLogin(
                                currentAccount.getId(),
                                currentAccount.getEmail()));

                String accessToken = securityUtil.createAccessToken(email, res);
                res.setAccessToken(accessToken);

                String new_refresh_token = securityUtil.createRefreshToken(email, res);
                this.accountService.updateToken(email, new_refresh_token);
                res.setAccount(null);
                ResponseCookie resCookie = ResponseCookie
                                .from("refresh_token", new_refresh_token)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(refreshTokenExpiration)
                                .build();

                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                                .body(DataResponse.<LoginResponse>builder()
                                                .status(200)
                                                .message("Refresh token successful")
                                                .data(Collections.singletonList(res))
                                                .build());
        }

        @Override
        public ResponseEntity<DataResponse<ValidateTokenResponse>> validateToken(String token) {
                boolean isValid = securityUtil.checkValidRefreshToken(token) != null;
                if (isValid) {
                        String email = jwtDecoder.decode(token).getSubject();
                        Account currentUserDB = accountService.getAccountByEmail(email).orElse(null);
                        if (currentUserDB != null) {
                                List<PermissionAuthResponse> permissions = currentUserDB.getRoles().stream()
                                                .map(Role::getPermissions)
                                                .flatMap(List::stream)
                                                .map(permission -> new PermissionAuthResponse(permission.getApiPath(),
                                                                permission.getMethod()))
                                                .toList();

                                return ResponseEntity.ok(DataResponse.<ValidateTokenResponse>builder()
                                                .status(200)
                                                .message("Token is valid")
                                                .data(Collections.singletonList(
                                                                new ValidateTokenResponse(true, currentUserDB.getId(),
                                                                                currentUserDB.getEmail(), permissions)))
                                                .build());
                        } else {
                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                .body(DataResponse.<ValidateTokenResponse>builder()
                                                                .status(401)
                                                                .message("Token is invalid")
                                                                .data(Collections.singletonList(
                                                                                new ValidateTokenResponse(false, null,
                                                                                                null, null)))
                                                                .build());
                        }
                } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(DataResponse.<ValidateTokenResponse>builder()
                                                        .status(401)
                                                        .message("Token is invalid")
                                                        .data(Collections.singletonList(new ValidateTokenResponse(false,
                                                                        null, null, null)))
                                                        .build());
                }
        }

}
