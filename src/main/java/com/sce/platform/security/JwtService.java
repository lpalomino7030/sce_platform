package com.sce.platform.security;

import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final JwtProperties properties;

    public JwtService(JwtProperties properties) {

        if (properties.secret() == null || properties.secret().isBlank()) {
            throw new IllegalStateException("JWT_SECRET no está configurado");
        }

        this.properties = properties;

        this.secretKey = Keys.hmacShaKeyFor(
             properties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(
         UUID usuarioId,
         UUID tenantId,
         UsuarioTenantRole role
    ) {

        Instant ahora = Instant.now();

        return Jwts.builder()
             .subject(usuarioId.toString())
             .issuer("sce-platform")
             .issuedAt(java.util.Date.from(ahora))
             .expiration(
                  java.util.Date.from(
                       ahora.plus(
                            properties.expirationMinutes(),
                            ChronoUnit.MINUTES
                       )
                  )
             )
             .claim("tenantId", tenantId.toString())
             .claim("role", role.name())
             .signWith(secretKey)
             .compact();
    }
}