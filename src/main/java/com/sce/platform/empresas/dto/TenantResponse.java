package com.sce.platform.empresas.dto;

import com.sce.platform.empresas.enums.TenantEstado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TenantResponse
{
    private UUID id;
    private String nombre;
    private String razonSocial;
    private String ruc;
    private String slug;
    private TenantEstado estado;
}
