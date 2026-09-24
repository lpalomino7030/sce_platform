package com.sce.platform.usuarios.controller;

import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CrearUsuarioTenantResponse {
    private UUID usuarioId;
    private String nombreUsuario;
    private String identificadorSce;
    private UsuarioTenantRole role;
    private String passwordTemporal;
}
