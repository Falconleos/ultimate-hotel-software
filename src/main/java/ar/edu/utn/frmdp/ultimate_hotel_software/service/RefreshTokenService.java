package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    String generateRefreshToken(String userEmail);

    String getSubjectAndMarkAsUsed(String token);

    void revokeToken(String token);

    void cleanupExpiredTokens();

    Optional<RefreshToken> findByToken(String token);
}
