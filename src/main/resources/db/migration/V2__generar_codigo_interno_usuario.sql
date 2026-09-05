
CREATE TABLE solicitudes_usuario_tenant (
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

usuario_id UUID NOT NULL,
tenant_solicitante_id UUID NOT NULL,
tenant_autorizador_id UUID NOT NULL,

rol_solicitado VARCHAR(30) NOT NULL,
estado VARCHAR(20) NOT NULL,

solicitado_por UUID NOT NULL,
resuelto_por UUID,

fecha_solicitud TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
fecha_resolucion TIMESTAMPTZ,

-- Relaciones (Llaves Foráneas)
CONSTRAINT fk_solicitudes_usuario_usuario
FOREIGN KEY (usuario_id)
REFERENCES usuarios(id)
ON DELETE CASCADE,

CONSTRAINT fk_solicitudes_usuario_tenant_solicitante
FOREIGN KEY (tenant_solicitante_id)
REFERENCES tenants(id)
ON DELETE CASCADE,

CONSTRAINT fk_solicitudes_usuario_tenant_autorizador
FOREIGN KEY (tenant_autorizador_id)
REFERENCES tenants(id)
ON DELETE CASCADE,

CONSTRAINT fk_solicitudes_usuario_solicitado_por
FOREIGN KEY (solicitado_por)
REFERENCES usuarios(id),

CONSTRAINT fk_solicitudes_usuario_resuelto_por
FOREIGN KEY (resuelto_por)
REFERENCES usuarios(id),

-- Restricciones de contenido (Checks para Enums)
CONSTRAINT ck_solicitudes_usuario_rol_solicitado
CHECK (rol_solicitado IN ('OWNER', 'ADMIN', 'USER')), -- Ajustar según los valores reales de tu UsuarioTenantRole

CONSTRAINT ck_solicitudes_usuario_estado
CHECK (estado IN ('PENDING', 'APPROVED', 'REJECTED', 'CANCELED')) -- Ajustar según los valores reales de tu SolicitudUsuarioTenantEstado
);