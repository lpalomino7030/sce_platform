package com.sce.platform.security;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sce.security.jwt")
public record JwtProperties(String secret, long expirationMinutes) {

}
