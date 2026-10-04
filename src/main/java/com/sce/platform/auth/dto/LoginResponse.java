package com.sce.platform.auth.dto;

import com.sce.platform.security.AuthContext;
import com.sce.platform.usuarios.enums.PlatformRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private UUID usuarioId;
    private String nombreUsuario;
    private String nombres;

    private TenantDisponibleResponse tenantSeleccionado;
    private List<TenantDisponibleResponse> tenants;

    private String token;
    private String selectionToken;

    private AuthContext context;
    private PlatformRole platformRole;
}
