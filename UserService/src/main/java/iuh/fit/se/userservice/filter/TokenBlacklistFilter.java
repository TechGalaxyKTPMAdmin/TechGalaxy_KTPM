package iuh.fit.se.userservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import iuh.fit.se.userservice.dto.response.DataResponse;
import iuh.fit.se.userservice.exception.ErrorCode;
import iuh.fit.se.userservice.provider.TokenProvider;
import iuh.fit.se.userservice.service.TokenService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenBlacklistFilter implements Filter {

    private final TokenProvider.TokenExtractor tokenExtractor;
    private final TokenService tokenService;
    private final ObjectMapper mapper;

    public TokenBlacklistFilter(TokenProvider.TokenExtractor tokenExtractor, TokenService tokenService, ObjectMapper mapper) {
        this.tokenExtractor = tokenExtractor;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = tokenExtractor.extract(httpRequest);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        if (tokenService.isTokenBlacklisted(token)) {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setContentType("application/json;charset=UTF-8");

            DataResponse<Object> res = new DataResponse<>();
            res.setStatus(ErrorCode.JWT_INVALID.getCode());
            res.setMessage(ErrorCode.JWT_INVALID.getMessage());
            mapper.writeValue(httpResponse.getWriter(), res);
            return;
        }

        chain.doFilter(request, response);
    }

}
