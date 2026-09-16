package com.sce.platform.security;

import com.sce.platform.usuarios.enums.UsuarioTenantRole;

import java.util.UUID;

public record SceAuthentication(
     UUID usuarioId,
     UUID tenantId,
     UsuarioTenantRole role
) {

}
