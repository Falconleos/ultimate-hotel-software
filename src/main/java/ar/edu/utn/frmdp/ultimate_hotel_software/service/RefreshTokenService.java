package ar.edu.utn.frmdp.ultimate_hotel_software.service;

public interface RefreshTokenService {

    String generateRefreshToken(String userEmail);

    String getSubjectAndMarkAsUsed(String token);

    void revokeToken(String token);

    void cleanupExpiredTokens();

}
