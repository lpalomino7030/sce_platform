package com.sce.platform.security;


import com.sce.platform.usuarios.enums.PlatformRole;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class SceJwtAuthenticationConverter  implements Converter<Jwt, SceAuthenticationToken> {

    @Override
    public SceAuthenticationToken convert(Jwt jwt) {

        UUID usuarioId =
                UUID.fromString(jwt.getSubject());

        AuthContext context =
                AuthContext.valueOf(
                        jwt.getClaimAsString("context")
                );

        if (context == AuthContext.PLATFORM) {

            PlatformRole role =
                    PlatformRole.valueOf(
                            jwt.getClaimAsString("platformRole")
                    );

            SceAuthentication authentication =
                    SceAuthentication.platform(
                            usuarioId,
                            role
                    );

            var authorities = List.of(
                    new SimpleGrantedAuthority(
                            "ROLE_PLATFORM_" + role.name()
                    )
            );

            return new SceAuthenticationToken(
                    authentication,
                    authorities
            );
        }

        UUID tenantId =
                UUID.fromString(
                        jwt.getClaimAsString("tenantId")
                );

        UsuarioTenantRole role =
                UsuarioTenantRole.valueOf(
                        jwt.getClaimAsString("tenantRole")
                );

        SceAuthentication authentication =
                SceAuthentication.tenant(
                        usuarioId,
                        tenantId,
                        role
                );

        var authorities = List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + role.name()
                )
        );

        return new SceAuthenticationToken(
                authentication,
                authorities
        );
    }
}