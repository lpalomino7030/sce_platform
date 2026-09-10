package com.sce.platform.auth.dto;


import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TenantSeleccionadoResponse {
    private UUID tenantId;
    private String nombre;
    private UsuarioTenantRole role;
}
