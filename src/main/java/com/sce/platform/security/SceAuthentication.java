package com.sce.platform.security;

import com.sce.platform.usuarios.enums.PlatformRole;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;

import java.util.UUID;

public record SceAuthentication(
        UUID usuarioId,
        AuthContext context,
        UUID tenantId,
        UsuarioTenantRole tenantRole,
        PlatformRole platformRole
) {

    public static SceAuthentication tenant(
            UUID usuarioId,
            UUID tenantId,
            UsuarioTenantRole role
    ) {
        return new SceAuthentication(
                usuarioId,
                AuthContext.TENANT,
                tenantId,
                role,
                null
        );
    }

    public static SceAuthentication platform(
            UUID usuarioId,
            PlatformRole role
    ) {
        return new SceAuthentication(
                usuarioId,
                AuthContext.PLATFORM,
                null,
                null,
                role
        );
    }
}