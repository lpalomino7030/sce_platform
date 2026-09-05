package com.sce.platform.usuarios.entity;


/*
SolicitudUsuarioTenant
────────────────────────────
id
usuarioId
tenantSolicitanteId
rolSolicitado
estado
solicitadoPor
resueltoPor
fechaSolicitud
fechaResolucion
 */

import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.usuarios.enums.SolicitudUsuarioTenantEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "solicitudes_usuario_tenant")
@Getter
@Setter
@NoArgsConstructor
public class SolicitudUsuarioTenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_solicitante_id", nullable = false)
    private Tenant tenantSolicitante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_autorizador_id", nullable = false)
    private Tenant tenantAutorizador;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_solicitado", nullable = false, length = 30)
    private UsuarioTenantRole rolSolicitado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private SolicitudUsuarioTenantEstado estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solicitado_por", nullable = false)
    private Usuario solicitadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resuelto_por")
    private Usuario resueltoPor;

    @CreatedDate
    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private Instant fechaSolicitud;

    @Column(name = "fecha_resolucion")
    private Instant fechaResolucion;


}
