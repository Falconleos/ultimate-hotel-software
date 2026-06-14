package ar.edu.utn.frmdp.ultimate_hotel_software.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {


    // Inyectamos la clave secreta que definimos en application.properties
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    // Inyectamos el tiempo de expiración (los 15 minutos)
    @Value("${jwt.expiration}")
    private Duration jwtExpiration;

    /**
     * Genera un token JWT firmado para un usuario que se acaba de autenticar con éxito.
     */
    public String generateAccessToken(UserDetails userDetails) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpiration.toMillis());

        // Extraemos la lista de roles del usuario (ej: ["ROLE_USER"]) para meterla adentro del token
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // El dueño del token
                .claim("roles", roles)                // Datos personalizados (roles)
                .setIssuedAt(now)                      // Fecha de creación
                .setExpiration(expirationDate)        // Fecha de vencimiento
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Firma digital de seguridad
                .compact();
    }

    /**
     * Extrae el nombre de usuario de adentro de un token.
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Verifica si el token enviado es válido (coincide el usuario y no está vencido).
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // --- MÉTODOS INTERNOS AUXILIARES ---

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Convierte nuestra clave de texto Base64 en una clave criptográfica real para firmar
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
