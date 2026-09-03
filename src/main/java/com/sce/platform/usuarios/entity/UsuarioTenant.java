package com.sce.platform.usuarios.entity;

import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.usuarios.enums.UsuarioTenantEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "usuarios_tenants")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class UsuarioTenant {

    @EmbeddedId
    private UsuarioTenantId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("tenantId")
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(
            name = "identificador_sce",
            nullable = false,
            unique = true,
            length = 150
    )
    private String identificadorSce;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private UsuarioTenantRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private UsuarioTenantEstado estado;

    @CreatedDate
    @Column(name = "fecha_union", nullable = false, updatable = false)
    private Instant fechaUnion;


}
