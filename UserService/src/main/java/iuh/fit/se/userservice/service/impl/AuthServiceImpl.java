package iuh.fit.se.userservice.service.impl;

import iuh.fit.se.userservice.dto.request.LoginRequest;
import iuh.fit.se.userservice.dto.response.LoginResponse;
import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.service.AuthService;
import iuh.fit.se.userservice.service.impl.AccountServiceImpl;
import iuh.fit.se.userservice.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountServiceImpl accountService;
    private final SecurityUtil securityUtil;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthServiceImpl(AuthenticationManagerBuilder authenticationManagerBuilder,
            AccountServiceImpl accountService,
            SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.accountService = accountService;
        this.securityUtil = securityUtil;
    }

    @Override
    public LoginResponse login(LoginRequest loginDto) throws AuthenticationException {
        log.info("Login attempt for user: {}", loginDto.getUsername());

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword());
            log.debug("Created authentication token for user: {}", loginDto.getUsername());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Authentication successful for user: {}", loginDto.getUsername());

            LoginResponse res = new LoginResponse();
            Account currentUserDB = accountService.getAccountByEmail(loginDto.getUsername()).orElse(null);
            if (currentUserDB != null) {
                log.debug("Found account details for user: {}", loginDto.getUsername());
                res.setAccount(new LoginResponse.AccountLogin(
                        currentUserDB.getId(),
                        currentUserDB.getEmail()));
            } else {
                log.warn("No account found for authenticated user: {}", loginDto.getUsername());
            }

            String accessToken = securityUtil.createAccessToken(authentication.getName(), res);
            res.setAccessToken(accessToken);
            log.debug("Access token created for user: {}", loginDto.getUsername());

            String refreshToken = securityUtil.createRefreshToken(loginDto.getUsername(), res);
            accountService.updateToken(refreshToken, loginDto.getUsername());
            log.debug("Refresh token updated for user: {}", loginDto.getUsername());

            log.info("Login successful for user: {}", loginDto.getUsername());
            return res;
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", loginDto.getUsername(), e);
            throw e;
        }
    }

    @Override
    public ResponseCookie buildRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();
    }
}
