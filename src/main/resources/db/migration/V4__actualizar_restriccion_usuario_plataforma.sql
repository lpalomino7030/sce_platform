ALTER TABLE usuario_plataforma
    DROP CONSTRAINT fk_usuario_plataforma;

ALTER TABLE usuario_plataforma
    ADD CONSTRAINT fk_usuario_plataforma
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE CASCADE;

ALTER TABLE usuario_plataforma
    ADD CONSTRAINT ck_usuario_plataforma_role
        CHECK (
            role_plataforma IN (
                'ADMIN',
                'SUPPORT',
                'BILLING'
            )
        );