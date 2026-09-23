package com.sce.platform.usuarios.dto;

import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrearUsuarioTenantRequest {
    @NotBlank
    @Size(max = 50)
    private String nombreUsuario;

    @NotBlank
    @Email
    @Size(max = 150)
    private String correo;

    @Size(max = 100)
    private String nombres;

    @Size(max = 100)
    private String apellidos;

    @NotNull
    private UsuarioTenantRole role;
}
