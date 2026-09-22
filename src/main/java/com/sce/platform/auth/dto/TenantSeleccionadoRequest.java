package com.sce.platform.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TenantSeleccionadoRequest {

    @NotNull
    private UUID tenantId;

    @NotBlank
    private String selectionToken;

}
