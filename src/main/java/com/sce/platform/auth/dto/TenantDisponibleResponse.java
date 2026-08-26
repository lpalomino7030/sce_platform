package com.sce.platform.auth.dto;

import com.sce.platform.usuarios.entity.UsuarioTenantRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class TenantDisponibleResponse {
    private UUID id;
    private String nombre;
    private UsuarioTenantRole role;
}
