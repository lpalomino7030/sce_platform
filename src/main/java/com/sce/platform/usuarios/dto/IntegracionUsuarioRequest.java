package com.sce.platform.usuarios.dto;


import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IntegracionUsuarioRequest {
    @NotBlank
    private String codigoSce;

    @NotNull
    private UsuarioTenantRole rolSolicitado;

}
