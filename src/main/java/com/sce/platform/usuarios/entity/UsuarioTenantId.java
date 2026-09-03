package com.sce.platform.usuarios.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsuarioTenantId {

    private UUID tenantId;
    private UUID usuarioId;

}
