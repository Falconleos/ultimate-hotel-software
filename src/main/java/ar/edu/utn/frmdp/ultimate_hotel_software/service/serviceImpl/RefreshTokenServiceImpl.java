package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.exception.InvalidTokenException;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.RefreshToken;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.RefreshTokenRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private Duration refreshTokenExpiration;

    @Override
    public String generateRefreshToken(String userEmail) {
        // 1. Limpiamos antiguos
        refreshTokenRepository.revokeAllByUserEmail(userEmail);

        // 2. Generamos el token
        String token = generateSecureToken();

        // 3. LOG DE DIAGNÓSTICO
        log.info("DEBUG: Intentando persistir token: {}", token);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .subject(userEmail)
                .expiresAt(Instant.now().plusMillis(refreshTokenExpiration.toMillis()))
                .createdAt(Instant.now())
                .revoked(false)
                .build();

        // 4. Guardamos y capturamos
        RefreshToken saved = refreshTokenRepository.save(refreshToken);

        // 5. SEGUNDO LOG
        log.info("DEBUG: Token recuperado del save(): {}", saved.getToken());

        return saved.getToken();
    }

    @Override
    public String getSubjectAndMarkAsUsed(String token) {
        // 1. BUSCAR SIN FILTROS: Esto es obligatorio para saber si existe y si está revocado
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Token no encontrado"));

        // 2. VALIDAR ESTADO: Ahora sí validamos la revocación manualmente
        if (refreshToken.isRevoked()) {
            throw new InvalidTokenException("El token ha sido revocado (Logout previo)");
        }

        // 3. VALIDAR EXPIRACIÓN
        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
            throw new InvalidTokenException("Refresh token expirado");
        }

        // 4. MARCAR COMO USADO
        refreshToken.setUsedAt(Instant.now());
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        return refreshToken.getSubject();
    }

    public void revokeToken(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevoked(true);
                    refreshTokenRepository.save(refreshToken);
                    refreshTokenRepository.flush(); // <--- Fuerza la escritura física
                    log.info("[RefreshTokenService] Token {} revocado y guardado", token);
                });
    }

    // Programamos una tarea que se ocupe de borrar de la base de datos
    // todos los tokens expirados. Correrá diariamente a las 2 AM
    @Override
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupExpiredTokens() {
        log.debug("[RefreshTokenService] Cleaning up expired tokens");
        refreshTokenRepository.deleteByExpiresAtBefore(Instant.now());
    }

    private String generateSecureToken() {
        try {
            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(SecureRandom.getInstanceStrong().generateSeed(64));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not generate secure refresh token", e);
        }
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
