package com.sce.platform.empresas.dto;

import com.sce.platform.usuarios.dto.CrearUsuarioRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TenantRequest {
    @NotBlank
    @Size(max = 150)
    private String nombre;
    @NotBlank
    @Size(max = 200)
    private String razonSocial;
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "El RUC debe contener exactamente 11 dígitos")
    private String ruc;

    // propietario inicial
    private CrearUsuarioRequest usuario;
}
