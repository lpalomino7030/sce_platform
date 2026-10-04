CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE tenants (
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

nombre VARCHAR(150) NOT NULL,
razon_social VARCHAR(200) NOT NULL,
ruc VARCHAR(11) NOT NULL,
slug VARCHAR(100) NOT NULL,
estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
fecha_actualizacion TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

CONSTRAINT uq_tenants_ruc
 UNIQUE (ruc),

CONSTRAINT uq_tenants_slug
 UNIQUE (slug),

CONSTRAINT ck_tenants_estado
 CHECK (
     estado IN (
                'ACTIVE',
                'SUSPENDED',
                'INACTIVE'
         )
     )
);

CREATE TABLE usuarios (
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
codigo_sce VARCHAR(10) NOT NULL,
nombre_usuario VARCHAR(50) NOT NULL,
correo VARCHAR(150) NOT NULL,
password_hash VARCHAR(255) NOT NULL,
nombres VARCHAR(100),
apellidos VARCHAR(100),
estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
fecha_actualizacion TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

CONSTRAINT uq_usuarios_nombre_usuario
  UNIQUE (nombre_usuario),

CONSTRAINT uq_usuarios_correo
  UNIQUE (correo),

CONSTRAINT uq_codigo_sce
    UNIQUE (codigo_sce),

CONSTRAINT ck_usuarios_estado
  CHECK (
      estado IN (
                 'ACTIVE',
                 'LOCKED',
                 'DISABLED'
          )
      )
);

CREATE TABLE usuarios_tenants (
      tenant_id UUID NOT NULL,
      usuario_id UUID NOT NULL,

      identificador_sce VARCHAR(150) NOT NULL,

      role VARCHAR(30) NOT NULL DEFAULT 'USER',

      estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

      fecha_union TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

      CONSTRAINT pk_usuarios_tenants
          PRIMARY KEY (tenant_id, usuario_id),

      CONSTRAINT fk_usuarios_tenants_tenant
          FOREIGN KEY (tenant_id)
              REFERENCES tenants(id)
              ON DELETE CASCADE,

      CONSTRAINT fk_usuarios_tenants_usuario
          FOREIGN KEY (usuario_id)
              REFERENCES usuarios(id)
              ON DELETE CASCADE,

      CONSTRAINT uq_usuarios_tenants_identificador_sce
          UNIQUE (identificador_sce),

      CONSTRAINT ck_usuarios_tenants_role
          CHECK (
              role IN (
                       'OWNER',
                       'ADMIN',
                       'USER'
                  )
              ),

      CONSTRAINT ck_usuarios_tenants_estado
          CHECK (
              estado IN (
                         'ACTIVE',
                         'SUSPENDED',
                         'REMOVED'
                  )
              )
);