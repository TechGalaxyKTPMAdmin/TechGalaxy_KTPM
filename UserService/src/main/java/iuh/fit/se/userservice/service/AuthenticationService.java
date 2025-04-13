package iuh.fit.se.userservice.service;

import iuh.fit.se.userservice.dto.request.LoginRequest;
import iuh.fit.se.userservice.dto.response.DataResponse;
import iuh.fit.se.userservice.dto.response.LoginResponse;
import iuh.fit.se.userservice.dto.response.ValidateTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<DataResponse<LoginResponse>> login(LoginRequest loginDto);

    ResponseEntity<DataResponse<LoginResponse.AccountGetAccount>> getAccount();

    ResponseEntity<DataResponse<String>> logout(HttpServletRequest request);

    ResponseEntity<DataResponse<LoginResponse>> refreshToken(String refreshToken);

    ResponseEntity<DataResponse<ValidateTokenResponse>> validateToken(String token);
}
