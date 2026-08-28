package com.sce.platform.empresas.dto;

import jakarta.validation.constraints.NotBlank;
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
    @Size(max = 11)
    private String ruc;
}
