package iuh.fit.se.userservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import iuh.fit.se.userservice.dto.response.DataResponse;

import iuh.fit.se.userservice.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);
        response.setContentType("application/json;charset=UTF-8");
        ErrorCode errorCode = ErrorCode.JWT_INVALID;
        DataResponse<Object> res = new DataResponse<>();
        res.setStatus(errorCode.getCode());
        res.setMessage(errorCode.getMessage());
        mapper.writeValue(response.getWriter(), res);
    }
}