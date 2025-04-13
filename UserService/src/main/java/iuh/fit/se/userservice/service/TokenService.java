package iuh.fit.se.userservice.service;


public interface TokenService {

    void blacklistToken(String token);

    boolean isTokenBlacklisted(String token);

    void cleanExpiredTokens();

}