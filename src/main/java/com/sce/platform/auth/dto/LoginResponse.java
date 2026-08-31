package com.sce.platform.auth.dto;

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
}
