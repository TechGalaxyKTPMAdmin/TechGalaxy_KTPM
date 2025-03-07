package iuh.fit.se.userservice.service;

import iuh.fit.se.userservice.dto.request.LoginRequest;
import iuh.fit.se.userservice.dto.response.LoginResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {
    /**
     * Xử lý đăng nhập người dùng.
     *
     * @param loginDto đối tượng chứa thông tin đăng nhập.
     * @return đối tượng LoginResponse chứa access token và thông tin tài khoản.
     * @throws AuthenticationException nếu thông tin đăng nhập không hợp lệ.
     * author: Vũ Nguyễn Minh Đức
     */
    LoginResponse login(LoginRequest loginDto) throws AuthenticationException;

    /**
     * Tạo cookie chứa refresh token.
     *
     * @param refreshToken refresh token.
     * @return ResponseCookie đã được cấu hình.
     * author: Vũ Nguyễn Minh Đức
     */
    ResponseCookie buildRefreshTokenCookie(String refreshToken);
}
