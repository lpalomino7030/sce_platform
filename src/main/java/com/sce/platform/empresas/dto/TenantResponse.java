package com.sce.platform.empresas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TenantResponse
{
    private String nombre;
    private String razonSocial;
    private String ruc;
    private String slug;
}
