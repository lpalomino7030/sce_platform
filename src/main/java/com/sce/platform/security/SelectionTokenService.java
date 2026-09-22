package com.sce.platform.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class SelectionTokenService {
    private static final String ISSUER = "sce-platform";
    private static final String PURPOSE = "TENANT_SELECTION";
    private static final long EXPIRATION_MINUTES = 5;

    private final SecretKey secretKey;

    public SelectionTokenService(JwtProperties properties) {
        if (properties.secret() == null || properties.secret().isBlank()) {
            throw new IllegalStateException("JWT_SECRET no está configurado");
        }

        this.secretKey = Keys.hmacShaKeyFor(
             properties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(UUID usuarioId) {

        Instant ahora = Instant.now();

        return Jwts.builder()
             .subject(usuarioId.toString())
             .issuer(ISSUER)
             .claim("purpose", PURPOSE)
             .issuedAt(java.util.Date.from(ahora))
             .expiration(java.util.Date.from(
                  ahora.plus(EXPIRATION_MINUTES, ChronoUnit.MINUTES)
             ))
             .signWith(secretKey)
             .compact();
    }

    public UUID validateAndGetUsuarioId(String token) {

        var claims = Jwts.parser()
             .verifyWith(secretKey)
             .requireIssuer(ISSUER)
             .require("purpose", PURPOSE)
             .build()
             .parseSignedClaims(token)
             .getPayload();

        return UUID.fromString(claims.getSubject());
    }
}
