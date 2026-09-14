package com.sce.platform.security;

import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SceJwtAuthenticationConverter  implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtAuthenticationConverter delegate = new JwtAuthenticationConverter();

    public SceJwtAuthenticationConverter() {
        delegate.setJwtGrantedAuthoritiesConverter(jwt -> {
            String role = jwt.getClaimAsString("role");

            if (role == null || role.isBlank()) {
                return java.util.List.of();
            }

            return java.util.List.of(
                 new SimpleGrantedAuthority("ROLE_" + role)
            );
        });
    }

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        return delegate.convert(jwt);
    }

}
