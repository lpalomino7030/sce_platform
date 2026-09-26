
CREATE TABLE usuario_plataforma
(
    id UUID PRIMARY KEY NOT NULL ,
    usuario_id UUID NOT NULL UNIQUE,
    role_plataforma VARCHAR(50) NOT NULL,

    CONSTRAINT fk_usuario_plataforma
FOREIGN KEY (usuario_id) REFERENCES usuarios(id)


);
